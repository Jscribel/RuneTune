import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jscribel.Database

actual class DatabaseDriverFactory(private val context : Context) {
    actual fun create() : SqlDriver = AndroidSqliteDriver(Database.Schema, context, "Database")
}