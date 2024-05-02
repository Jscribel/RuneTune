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
        return if(frequency < 0){
            Pitch("Auto", -1, -1.0, -1.0)
        }
        else{
            val pitch = queries.getClosest(frequency).executeAsOne()
            Pitch(pitch.note, pitch.octave, pitch.frequency, 1200 * log2(frequency/pitch.frequency))
        }
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