import dataSources.PitchDataSource
import dataSources.SettingDataSource
import dataSources.TuningDataSource

expect class AppModule {
    fun provideSettingDataSource() : SettingDataSource
    fun providePitchDataSource() : PitchDataSource
    fun provideTuningDataSource() : TuningDataSource
}