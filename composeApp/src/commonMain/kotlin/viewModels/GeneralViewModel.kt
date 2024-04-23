package viewModels

import AppModule
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import classes.Pitch
import moe.tlaster.precompose.viewmodel.ViewModel
import classes.Theme
import dataSources.PitchDataSource
import dataSources.SettingDataSource


private fun toTheme(nameTheme : String) : Theme {
    if(nameTheme.equals("dark")){
        return Theme.DARK
    }
    return Theme.LIGHT
}
private fun toAlteration(value : String) : Byte {
    if(value.equals("sharp")){
        return 0
    }
    return 1
}

class GeneralViewModel(internal val appModule: AppModule) : ViewModel(){
    private val settingDataSource = appModule.provideSettingDataSource()

    private var theme = toTheme(settingDataSource.get("theme"))
    private var alteration = toAlteration(settingDataSource.get("alteration"))

    var pitch by mutableStateOf(Pitch("C", 0, 0.0, -2147483648.0))

    var backgroundColor by mutableStateOf(Color(theme.bgRed, theme.bgGreen, theme.bgBlue))
        private set
    var foregroundColor by mutableStateOf(Color(theme.fgRed, theme.fgGreen, theme.fgBlue))
        private set
    var highlightColor by mutableStateOf(Color(theme.hlRed, theme.hlGreen, theme.hlBlue))
        private set

    fun changeTheme(nameTheme : String){
        theme = toTheme(nameTheme)
        settingDataSource.update("theme", nameTheme)
        backgroundColor = Color(theme.bgRed, theme.bgGreen, theme.bgBlue)
        foregroundColor = Color(theme.fgRed, theme.fgGreen, theme.fgBlue)
        highlightColor = Color(theme.hlRed, theme.hlGreen, theme.hlBlue)
    }
    fun changeAlteration(value : String){
        alteration = toAlteration(value)
        settingDataSource.update("alteration", value)
    }

    fun changePitch(pitch: Pitch){
        this.pitch = pitch
    }

    fun displayPitch(pitch : Pitch) : String{
        return if (pitch.getNote().length > 1)
            pitch.getNote().slice((alteration * 3)..(1 + alteration * 3)) + " " + pitch.getOctave()
        else pitch.getNote() + " " + pitch.getOctave()
    }
}