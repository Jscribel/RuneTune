import android.app.Activity
import android.content.Context
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.filters.LowPassFS
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchDetectionResult
import be.tarsos.dsp.pitch.PitchProcessor
import com.jscribel.Database
import dataSources.PitchDataSource
import dataSources.SettingDataSource
import dataSources.TuningDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import viewModels.GeneralViewModel

actual class AppModule(private val context : Context){

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
        val pitchDetectionHandler = PitchDetectionHandler{ result : PitchDetectionResult, e : AudioEvent ->
            val pitchResult = result.pitch
            (context as Activity).runOnUiThread {
                generalViewModel.changePitch(PitchDataSource(database).getClosest(pitchResult.toDouble()))
            }
        }
        val pitchProcessor = PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050f, 2048, pitchDetectionHandler)
        dispatcher.addAudioProcessor(LowPassFS(50f, 10000f))
        dispatcher.addAudioProcessor(pitchProcessor)
        Thread(dispatcher, "Pitch Detector").start()
    }
}