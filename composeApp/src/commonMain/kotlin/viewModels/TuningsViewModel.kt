package viewModels

import AppModule
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import classes.Pitch
import dataSources.TuningDataSource
import moe.tlaster.precompose.viewmodel.ViewModel

class TuningsViewModel(generalViewModel: GeneralViewModel) : ViewModel(){
    val tuningDataSource = generalViewModel.appModule.provideTuningDataSource()

    var tuningsWidth by mutableStateOf(0)
        private set

    var tuning by mutableStateOf(tuningDataSource.getTuning(1))
    var pitch by mutableStateOf(Pitch("E", 82.0, 2))
        private set

    fun changeTuningsWidth(width : Int){
        tuningsWidth = width
    }

    fun changePitch(pitch : Pitch){
        this.pitch = pitch
    }
}