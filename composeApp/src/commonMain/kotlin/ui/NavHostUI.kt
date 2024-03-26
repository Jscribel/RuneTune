package ui

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import viewModels.GeneralViewModel

@Composable
fun navHostUI(navigator : Navigator, generalViewModel :GeneralViewModel){
    NavHost(navigator = navigator, navTransition =  NavTransition(), initialRoute = "/tuner") {
        scene(route = "/tuner") {
            tunerUI()
        }
        scene(route = "/tunings") {
            tuningsUI()
        }
        scene(route = "/settings") {
            settingUI(generalViewModel)
        }
    }
}