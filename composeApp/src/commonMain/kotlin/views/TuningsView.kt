package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import classes.Pitch
import moe.tlaster.precompose.viewmodel.viewModel
import viewModels.GeneralViewModel
import viewModels.TuningsViewModel
import kotlin.math.roundToInt

@Composable
fun tuningsView(generalViewModel: GeneralViewModel) {
    val tuningsViewModel = viewModel(modelClass = TuningsViewModel::class){
        TuningsViewModel(generalViewModel)
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        //display
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            FloatingActionButton(
                onClick = {

                },
                backgroundColor = generalViewModel.highlightColor,
                contentColor = generalViewModel.foregroundColor
            ) {
                Text(
                    text = tuningsViewModel.pitch.toString()
                )
            }
            Text(text = tuningsViewModel.pitch.getOffset().roundToInt().toString())
        }

        //Pitches
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            items((tuningsViewModel.tuning.getPitches().size + 2)/3){ indexColumn ->
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    var countButton : Int = tuningsViewModel.tuning.getPitches().size - (3 * indexColumn)
                    if(countButton > 3){
                        countButton = 3
                    }
                    items(countButton){indexButton ->
                        Button(
                            onClick = {
                                tuningsViewModel.changePitch(tuningsViewModel.tuning.getPitches()[(indexColumn * 3 + indexButton)])
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(backgroundColor = generalViewModel.highlightColor, contentColor = generalViewModel.foregroundColor)
                        ) {
                            Text(
                                text = tuningsViewModel.tuning.getPitches()[(indexColumn * 3 + indexButton)].toString()
                            )
                        }
                    }
                }
            }
        }
    }
}