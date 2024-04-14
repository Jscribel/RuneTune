package dataSources

import classes.Pitch
import com.jscribel.Database
import classes.Tuning
import com.jscribel.Tuning_pitch

class TuningDataSource(private val database: Database){
    private val queriesTuning = database.tuningQueries
    private val queriesTuningPitches = database.tuning_pitchQueries
    private val queriesPitch = database.pitchQueries

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

    fun getTuning(id : Long) : Tuning{
        val tuningName = queriesTuning.getByID(id).executeAsOne().tuning_name
        val pitchRows = queriesTuningPitches.getByTuningID(id).executeAsList()
        val pitches = mutableListOf<Pitch>()
        for(pitch in pitchRows) {
            val pitchRowTemp = queriesPitch.getByKey(pitch.note, pitch.octave).executeAsOne()
            pitches.add(
                Pitch(
                    note = pitchRowTemp.note,
                    frequencyRelative = pitchRowTemp.frequency,
                    octave = pitchRowTemp.octave,
                    offset = pitch.pitch_offset
                )
            )
        }
        return Tuning(tuningName, pitches)
    }
    fun getAll() : List<Tuning>{
        val tunings = mutableListOf<Tuning>()
        var pitchData = queriesTuningPitches.getAll().executeAsList()
        for(pitch in pitchData) {
            if(pitch.tuning_id > tunings.size - 1){
                tunings.add(Tuning(queriesTuning.getByID(pitch.tuning_id).executeAsOne().tuning_name))
            }
            tunings.get(pitch.tuning_id.toInt()).getPitches().add(Pitch(pitch.note, pitch.octave, queriesPitch.getByKey(pitch.note, pitch.octave).executeAsOne().frequency, pitch.pitch_offset))//truncation theoretically could cause issues
        }
        return tunings
    }

    fun update(tuningID : Long, tuning : Tuning){//implement more efficient way
        if(tuning.getName().equals(queriesTuning.getByID(tuningID).executeAsOne().tuning_name)){
            queriesTuning.update(tuning.getName(), tuningID)
        }

        var sizeCurrent = queriesTuningPitches.getCount(tuningID).executeAsOne()
        var sizeNew : Long = tuning.getPitches().size.toLong()
        while(sizeNew + sizeCurrent > 0){
            if(sizeNew == sizeCurrent){
                if(!tuning.getPitch((sizeNew).toInt()).getNote().equals(queriesTuning.getByID(tuningID).executeAsOne().tuning_name)){
                    queriesTuningPitches.update(tuning.getPitch(sizeNew.toInt()).getNote(), tuning.getPitch(sizeNew.toInt()).getOctave(), tuning.getPitch(sizeNew.toInt()).getOffset(), tuningID, sizeNew)
                    sizeNew--
                    sizeCurrent--
                }
            }
            else if(sizeNew > sizeCurrent){
                queriesTuningPitches.insert(tuningID, sizeNew, tuning.getPitch(sizeNew.toInt()).getNote(), tuning.getPitch(sizeNew.toInt()).getOctave(), tuning.getPitch(sizeNew.toInt()).getOffset())//truncation theoretically could cause issues
                sizeNew--
            }
            else{
                queriesTuningPitches.delete(tuningID, sizeCurrent)
                sizeCurrent--
            }
        }
    }

    fun delete(tuningID : Long){
        queriesTuningPitches.deleteTuning(tuningID)
        queriesTuning.delete(tuningID)
    }
}