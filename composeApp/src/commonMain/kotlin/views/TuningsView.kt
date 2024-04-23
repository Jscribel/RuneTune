package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import kotlin.math.log2
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
        //options bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("Tuning")
            Button(
                onClick = {
                    tuningsViewModel.changeTuning(0)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = generalViewModel.highlightColor, contentColor = generalViewModel.foregroundColor)
            ){
                Text(text = "E-Standard")
            }
            Button(
                onClick = {
                    tuningsViewModel.changeTuning(1)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = generalViewModel.highlightColor, contentColor = generalViewModel.foregroundColor)
            ){
                Text(text = "Drop D")
            }
        }

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
                    text = generalViewModel.displayPitch(tuningsViewModel.pitch)
                )
            }
            Text(text = if(generalViewModel.pitch.getOffset() == -1.0) "" else (1200 * log2((generalViewModel.pitch.getFrequency())/(tuningsViewModel.pitch.getFrequency()))).roundToInt().toString())
        }

        //Pitches
        val sizeTuning = tuningsViewModel.tuning.getPitches().size
        val numberButtons = if(sizeTuning > 10) 5 else sizeTuning/2
        var countButton : Int
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            items((sizeTuning + 2)/numberButtons){ indexColumn ->
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    countButton = sizeTuning - (numberButtons * indexColumn)
                    if(countButton > numberButtons){
                        countButton = numberButtons
                    }
                    items(countButton){indexButton ->
                        Button(
                            onClick = {
                                tuningsViewModel.changeString((indexColumn * numberButtons) + countButton - indexButton)
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(backgroundColor = generalViewModel.highlightColor, contentColor = generalViewModel.foregroundColor)
                        ) {
                            Text(
                                text = generalViewModel.displayPitch(tuningsViewModel.tuning.getPitch((indexColumn * numberButtons) + countButton - indexButton))
                            )
                        }
                    }
                }
            }
        }
    }
}