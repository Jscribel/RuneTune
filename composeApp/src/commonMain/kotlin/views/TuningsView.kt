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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.viewmodel.viewModel
import viewModels.GeneralViewModel

val pitches : List<String> = listOf("D", "E", "A", "D", "G", "B", "E")//test hardcode

@Composable
fun tuningsView() {
    val generalViewModel = viewModel(modelClass = GeneralViewModel::class){
        GeneralViewModel()
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tuning Sets Page")
        Text(text = "Pitch")
        Text(text = "0")
        LazyRow(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            items((pitches.size + 2)/3){indexColumn ->
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    var countButton : Int = pitches.size - (3 * indexColumn)
                    if(countButton > 3){
                        countButton = 3
                    }
                    items(countButton){indexButton ->
                        Button(
                            onClick = {

                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(backgroundColor = generalViewModel.highlightColor, contentColor = generalViewModel.foregroundColor)
                        ) {
                            Text(
                                text = pitches[(indexColumn * 3 + indexButton)]
                            )
                        }
                    }
                }
            }
        }
    }
}