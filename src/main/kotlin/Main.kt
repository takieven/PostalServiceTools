import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.screens.About
import ui.screens.Complete
import ui.screens.Home

@Composable
fun App() {
    var screen by remember { mutableStateOf(SCREENS.HOME) }

    MaterialTheme {
            Row(
                Modifier.background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    IconToggleButton(
                        checked = screen == SCREENS.HOME,
                        onCheckedChange = {
                            screen = SCREENS.HOME
                        }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "home")
                    }
                    IconToggleButton(
                        checked = screen == SCREENS.COMPLETE,
                        onCheckedChange = {
                            screen = SCREENS.COMPLETE
                        }
                    ) {
                        Icon(imageVector = Icons.Default.FindInPage, "home")
                    }
                    IconToggleButton(
                        checked = screen == SCREENS.ABOUT,
                        onCheckedChange = {
                            screen = SCREENS.ABOUT
                        }
                    ) {
                        Icon(imageVector = Icons.Default.AccountBox, "home")
                    }
                }

                when (screen) {
                    SCREENS.HOME -> Home()
                    SCREENS.ABOUT-> About()
                    SCREENS.COMPLETE -> Complete()
                }
            }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CheckMate USPS",
    ) {
        Surface(
            color = Color(55, 55, 55),
        ) {
            App()
        }
    }
}

enum class SCREENS {
    HOME,
    COMPLETE,
    ABOUT,
}