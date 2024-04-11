package dataSources

import com.jscribel.Database
import com.jscribel.SettingQueries

class SettingDataSource(private val database: Database){
    private val queries : SettingQueries = database.settingQueries

    fun get(name: String) = queries.getValue(name = name).executeAsOne()
    fun getAll() = queries.getAll().executeAsList()
    fun update(name: String, value: String){
        queries.update(value = value, name = name)
    }
}