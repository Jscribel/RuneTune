package dataSources

import classes.Pitch
import com.jscribel.Database
import classes.Tuning

class TuningDataSource(private val database: Database){
    private val queriesTuning = database.tuningQueries
    private val queriesTuningPitches = database.tuning_pitchQueries
    private val queriesPitch = database.pitchQueries

    fun getTuning(id : Long) : Tuning{
        val tuningName = queriesTuning.getByID(id).executeAsOne().tuning_name
        val pitchRows = queriesTuningPitches.getByTuningID(id).executeAsList()
        var pitches = mutableListOf<Pitch>()
        for(pitch in pitchRows){
            val pitchRowTemp = queriesPitch.getByKey(pitch.note, pitch.octave).executeAsOne()
            pitches.add(Pitch(pitchRowTemp.note, pitchRowTemp.frequency, pitchRowTemp.octave, pitch.pitch_offset))
        }
        return Tuning(tuningName, pitches)
    }

    fun insertAll(name : String, pitches : List<Pitch>){
        queriesTuning.insert(name)
        val tuningID : Long = queriesTuning.getByName(name).executeAsOne().tuning_id

        var count : Long = 1
        for(pitch in pitches) {
            queriesTuningPitches.insert(tuningID, count++, pitch.getNote(), pitch.getOctave(), pitch.getOffset())
        }
    }
    fun insertAll(tuning : Tuning){
        queriesTuning.insert(tuning.getName())
        val tuningID : Long = queriesTuning.getByName(tuning.getName()).executeAsOne().tuning_id

        var count : Long = 1
        for(pitch in tuning.getPitches()) {
            queriesTuningPitches.insert(tuningID, count++, pitch.getNote(), pitch.getOctave(), pitch.getOffset())
        }
    }

    fun test(){
        val name : String = "E-standard"
        queriesTuning.insert(name)
        val tuningID : Long = queriesTuning.getByName(name).executeAsOne().tuning_id
        val pitches : List<Pitch> = listOf(Pitch("E", 82.0, 2), Pitch("A", 110.0, 2), Pitch("D", 147.0, 3), Pitch("G", 196.0, 3), Pitch("B", 247.0, 3), Pitch("E", 330.0, 4))

        var count : Long = 1
        for(pitch in pitches) {
            queriesTuningPitches.insert(tuningID, count++, pitch.getNote(), pitch.getOctave(), pitch.getOffset())
        }
    }
}