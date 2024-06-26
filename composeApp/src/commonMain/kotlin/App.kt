import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.viewmodel.viewModel
import viewModels.GeneralViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import views.bottomBarView
import views.navHostView

@Composable
@Preview
fun App(appModule: AppModule){
    PreComposeApp{
        MaterialTheme{
            val navigator = rememberNavigator()
            val generalViewModel = viewModel(modelClass = GeneralViewModel::class){
                GeneralViewModel(appModule)
            }
            appModule.startPitchDetector(generalViewModel)
            Scaffold(
                backgroundColor = generalViewModel.backgroundColor,
                contentColor = generalViewModel.foregroundColor,
                content = {
                    Text(generalViewModel.debugMessage)
                    navHostView(navigator, generalViewModel)
                },
                bottomBar = {
                    BottomAppBar(
                        backgroundColor = generalViewModel.highlightColor,
                        contentColor = generalViewModel.foregroundColor
                    ){
                        bottomBarView(navigator)
                    }
                }
            )
        }
    }
}