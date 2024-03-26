package viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import moe.tlaster.precompose.viewmodel.ViewModel

private enum class Theme(val bgRed : Int, val bgGreen: Int, val bgBlue: Int, val fgRed : Int, val fgGreen: Int, val fgBlue: Int, val hlRed : Int, val hlGreen: Int, val hlBlue: Int){
    DARK(0x4f,0x4f,0x4f,0xff,0xff,0xff,0x5f,0x5f,0x5f),
    LIGHT(0xe8,0xe8,0xe8,0x00,0x00,0x00,0xd8,0xd8,0xd8)
}

class SettingsViewModel : ViewModel(){
    private fun toTheme(nameTheme : String) : Theme{
        if(nameTheme.equals("dark")){
            return Theme.DARK
        }
        return Theme.LIGHT
    }

    private var theme : Theme = Theme.LIGHT

    var backgroundColor by mutableStateOf(Color(theme.bgRed, theme.bgGreen, theme.bgBlue))
        private set
    var foregroundColor by mutableStateOf(Color(theme.fgRed, theme.fgGreen, theme.fgBlue))
        private set
    var highlightColor by mutableStateOf(Color(theme.hlRed, theme.hlGreen, theme.hlBlue))
        private set


    public fun changeTheme(nameTheme : String){
        theme = toTheme(nameTheme)
        backgroundColor = Color(theme.bgRed, theme.bgGreen, theme.bgBlue)
        foregroundColor = Color(theme.fgRed, theme.fgGreen, theme.fgBlue)
        highlightColor = Color(theme.hlRed, theme.hlGreen, theme.hlBlue)
    }
}