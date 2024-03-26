package views

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import viewModels.GeneralViewModel

@Composable
fun navHostView(navigator : Navigator, generalViewModel :GeneralViewModel){
    NavHost(navigator = navigator, navTransition =  NavTransition(), initialRoute = "/tuner") {
        scene(route = "/tuner") {
            tunerView()
        }
        scene(route = "/tunings") {
            tuningsView()
        }
        scene(route = "/settings") {
            settingView(generalViewModel)
        }
    }
}