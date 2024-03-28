package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import runetune.composeapp.generated.resources.Res
import runetune.composeapp.generated.resources.TunerIcon

@OptIn(ExperimentalResourceApi::class)
@Composable
fun bottomBarView(navigator : Navigator){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
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
                imageVector = vectorResource(Res.drawable.TunerIcon),
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