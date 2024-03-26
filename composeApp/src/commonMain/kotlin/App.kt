import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.viewmodel.viewModel
import viewModels.GeneralViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.bottomBarUI
import ui.navHostUI

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    PreComposeApp(){
        MaterialTheme {
            val navigator = rememberNavigator()
            val generalViewModel = viewModel(modelClass = GeneralViewModel::class){
                GeneralViewModel()
            }
            Scaffold(
                backgroundColor = generalViewModel.backgroundColor,
                contentColor = generalViewModel.foregroundColor,
                content = {
                    navHostUI(navigator, generalViewModel)
                },
                bottomBar = {
                    BottomAppBar(
                        backgroundColor = generalViewModel.highlightColor,
                        contentColor = generalViewModel.foregroundColor
                    ) {
                        bottomBarUI(navigator)
                    }
                }
            )
        }
    }
}