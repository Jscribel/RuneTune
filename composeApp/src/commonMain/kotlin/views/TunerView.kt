package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.viewmodel.viewModel
import viewModels.GeneralViewModel
import viewModels.TunerViewModel
import kotlin.math.roundToInt

@Composable
fun tunerView(generalViewModel: GeneralViewModel) {
    val tunerViewModel = viewModel(modelClass = TunerViewModel::class){
        TunerViewModel()
    }
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FloatingActionButton(
            onClick = {

            },
            backgroundColor = generalViewModel.highlightColor,
            contentColor = generalViewModel.foregroundColor
        ){
            Text(
                text = tunerViewModel.pitch.toString()
            )
        }
        Text(text = tunerViewModel.pitch.getOffset().roundToInt().toString())

    }
}