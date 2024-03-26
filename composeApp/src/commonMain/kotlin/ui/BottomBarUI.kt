package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun bottomBarUI(navigator : Navigator){
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
                contentDescription = "TuningsUI"
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