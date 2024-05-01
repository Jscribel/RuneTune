import android.app.Activity
import android.content.Context
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.filters.LowPassFS
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchDetectionResult
import be.tarsos.dsp.pitch.PitchProcessor
import classes.Pitch
import com.jscribel.Database
import dataSources.PitchDataSource
import dataSources.SettingDataSource
import dataSources.TuningDataSource
import viewModels.GeneralViewModel

private data class ResultPair(val frequency : Int, val probability : Float)//make better name

actual class AppModule(private val context : Context){

    private var frequencies : MutableList<PitchDetectionResult> = mutableListOf()

    private fun resultProbability(frequency : Int, resultList : List<PitchDetectionResult>) : ResultPair{
        var probability = 0.0f
        for(result in resultList){
            probability += result.probability
        }
        return ResultPair(frequency, probability)
    }

    private fun addFrequency(result: PitchDetectionResult, generalViewModel: GeneralViewModel){// add duration to this
        if(frequencies.size >= 8){
            //if(frequencies[0].pitch == -1f){
                val resultGroupedList = (frequencies.groupBy{ r -> r.pitch.toInt() }.map { (frequency, resultList) ->  resultProbability(frequency, resultList)}).toMutableList()
                resultGroupedList.sortByDescending{ r -> r.probability}
                generalViewModel.changePitch(PitchDataSource(database).getClosest(resultGroupedList[0].frequency.toDouble()))
            //}
            frequencies.clear()
        }
        else {
            if(result.pitch >= 0 && result.probability > 0) {// find out how negatives are getting through
                frequencies.add(result)
            }
        }
    }

    private val database by lazy{
        Database(
            driver = DatabaseDriverFactory(context).create()
        )
    }

    actual fun provideSettingDataSource() = SettingDataSource(database)
    actual fun providePitchDataSource() = PitchDataSource(database)
    actual fun provideTuningDataSource() = TuningDataSource(database)

    actual fun startPitchDetector(generalViewModel: GeneralViewModel){//add permission checker
        val dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 2048, 0)
        val pitchDetectionHandler = PitchDetectionHandler{ result : PitchDetectionResult, _ : AudioEvent ->
            (context as Activity).runOnUiThread {
                if(result.pitch > 0){
                    addFrequency(result, generalViewModel)
                }
            }
        }
        val pitchProcessor = PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050f, 2048, pitchDetectionHandler)
        //dispatcher.addAudioProcessor(LowPassFS(50f, 10000f))
        dispatcher.addAudioProcessor(pitchProcessor)
        Thread(dispatcher, "Pitch Detector").start()
    }
}