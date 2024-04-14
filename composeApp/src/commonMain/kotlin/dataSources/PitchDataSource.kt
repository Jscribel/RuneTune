package dataSources

import com.jscribel.Database
import classes.Pitch
import kotlin.math.log2

class PitchDataSource(private val database: Database){
    private val queries = database.pitchQueries

    fun get(note : String, octave : Long) : Pitch{
        val pitch = queries.getByKey(note, octave).executeAsOne()
        return Pitch(pitch.note, pitch.octave, pitch.frequency)
    }

    fun getClosest(frequency : Double) : Pitch {
        val pitch = queries.getByFrequency(frequency).executeAsOneOrNull()
        if(pitch == null){
            val pitchHigh = queries.getNextByFrequency(frequency).executeAsOneOrNull()
            val pitchLow = queries.getPrevByFrequency(frequency).executeAsOneOrNull()

            return if(pitchHigh == null || pitchLow != null && pitchHigh.frequency - frequency > frequency - pitchLow.frequency)
                Pitch(pitchLow!!.note, pitchLow.octave, pitchLow.frequency, 1200 * log2(frequency/pitchLow.frequency))//why is !! needed

            else Pitch(pitchHigh.note, pitchHigh.octave, pitchHigh.frequency, 1200 * log2(frequency/pitchHigh.frequency))
        }
        else return Pitch(pitch.note, pitch.octave, pitch.frequency)
    }

    fun getAll() : List<Pitch>{
        val pitchRows = queries.getAll().executeAsList()
        var pitches = mutableListOf<Pitch>()
        for(pitch in pitchRows){
            pitches.add(Pitch(pitch.note, pitch.octave, pitch.frequency))
        }
        return pitches
    }
}