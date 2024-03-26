import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import moe.tlaster.precompose.viewmodel.viewModel
import viewModels.SettingsViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    PreComposeApp(){
        MaterialTheme {
            val navigator = rememberNavigator()
            val settingsViewModel = viewModel(modelClass = SettingsViewModel::class){
                SettingsViewModel()
            }
            Scaffold(
                backgroundColor = settingsViewModel.backgroundColor,
                contentColor = settingsViewModel.foregroundColor,
                content = {
                    NavHost(navigator = navigator, navTransition =  NavTransition(), initialRoute = "/tuner") {
                        scene(route = "/tuner") {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Tuner Page")
                                Text(text = "Pitch")
                                Text(text = "0")
                            }
                        }
                        scene(route = "/tunings") {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Tuning Sets Page")
                                Text(text = "Pitch")
                                Text(text = "0")
                            }
                        }
                        scene(route = "/settings") {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Settings Page")
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Text("Theme")
                                    Button(
                                        onClick = {
                                            settingsViewModel.changeTheme("dark")
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = settingsViewModel.highlightColor, contentColor = settingsViewModel.foregroundColor)
                                    ){
                                        Text(text = "dark")
                                    }
                                    Button(
                                        onClick = {
                                            settingsViewModel.changeTheme("light")
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = settingsViewModel.highlightColor, contentColor = settingsViewModel.foregroundColor)
                                    ){
                                        Text(text = "light")
                                    }
                                }
                            }
                        }
                    }
                },
                bottomBar = {
                    BottomAppBar(
                        backgroundColor = settingsViewModel.highlightColor,
                        contentColor = settingsViewModel.foregroundColor
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(
                                onClick = {
                                    navigator.navigate(
                                        "/tunings",
                                        NavOptions(
                                            launchSingleTop = true
                                        )
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Tunings"
                                )
                            }

                            IconButton(
                                onClick = {
                                    navigator.navigate(
                                        "/tuner",
                                        NavOptions(
                                            launchSingleTop = true
                                        )
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Tuner"
                                )
                            }

                            IconButton(
                                onClick = {
                                    navigator.navigate(
                                        "/settings",
                                        NavOptions(
                                            launchSingleTop = true
                                        )
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings"
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}