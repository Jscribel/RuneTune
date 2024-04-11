import com.jscribel.Database
import dataSources.PitchDataSource
import dataSources.SettingDataSource
import dataSources.TuningDataSource

actual class AppModule {
    private val database by lazy {
        Database(
            driver = DatabaseDriverFactory().create()
        )
    }
    actual fun provideSettingDataSource() = SettingDataSource(database)
    actual fun providePitchDataSource() = PitchDataSource(database)
    actual fun provideTuningDataSource() = TuningDataSource(database)
}