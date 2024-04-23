import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import viewModels.GeneralViewModel

actual class AppModule(private val context : Context) {
    private val database by lazy {
        Database(
            driver = DatabaseDriverFactory(context).create()
        )
    }

    actual fun provideSettingDataSource() = SettingDataSource(database)
    actual fun providePitchDataSource() = PitchDataSource(database)
    actual fun provideTuningDataSource() = TuningDataSource(database)

    actual fun startPitchDetector(generalViewModel: GeneralViewModel){//add permission checker
        val dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(4096, 4096, 0)
        val pitchDetectionHandler = PitchDetectionHandler{ result : PitchDetectionResult, e : AudioEvent ->
            val pitchResult = result.pitch
            (context as Activity).runOnUiThread {
                generalViewModel.changePitch(PitchDataSource(database).getClosest(pitchResult.toDouble()))
            }
        }
        val pitchProcessor = PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 4096f, 4096, pitchDetectionHandler)
        dispatcher.addAudioProcessor(LowPassFS(50f, 10000f))
        dispatcher.addAudioProcessor(pitchProcessor)
        Thread(dispatcher, "Pitch Detector").start()
    }
}