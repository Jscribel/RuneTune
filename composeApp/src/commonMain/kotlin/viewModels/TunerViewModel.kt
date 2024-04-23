package viewModels

import AppModule
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import classes.Pitch
import moe.tlaster.precompose.viewmodel.ViewModel

class TunerViewModel(generalViewModel: GeneralViewModel) : ViewModel(){
    val pitchDataSource =  generalViewModel.appModule.providePitchDataSource()
    var pitch by mutableStateOf(generalViewModel.pitch)
        private set
}