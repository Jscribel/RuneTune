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

    var tuning by mutableStateOf(tuningDataSource.getTuning(0))
        private set
    var pitch by mutableStateOf(tuning.getPitch(1))
        private set

    fun changeTuningsWidth(width : Int){
        tuningsWidth = width
    }

    fun changeTuning(tuningID : Long){
        tuning = tuningDataSource.getTuning(tuningID)
        pitch = tuning.getPitch(1)
    }

    fun changeString(string : Int){
        pitch = tuning.getPitch(string)
    }
}