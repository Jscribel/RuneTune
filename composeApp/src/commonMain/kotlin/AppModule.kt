import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import classes.Pitch
import dataSources.PitchDataSource
import dataSources.SettingDataSource
import dataSources.TuningDataSource
import viewModels.GeneralViewModel

expect class AppModule {
    fun provideSettingDataSource() : SettingDataSource
    fun providePitchDataSource() : PitchDataSource
    fun provideTuningDataSource() : TuningDataSource

    fun startPitchDetector(generalViewModel: GeneralViewModel)
}