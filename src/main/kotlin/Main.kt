import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.screens.About
import ui.screens.Home

@Composable
@Preview
fun App() {
    var screen by remember { mutableStateOf(SCREENS.HOME) }

    MaterialTheme {
            Row(
                Modifier.background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    IconButton(onClick = {
                        screen = SCREENS.HOME
                    }) {
                        Icon(imageVector = Icons.Default.Home, "home")
                    }
                    IconButton(onClick = {
                        screen = SCREENS.ABOUT
                    }) {
                        Icon(imageVector = Icons.Default.AccountBox, "home")
                    }
                }

                when (screen) {
                    SCREENS.HOME -> Home()
                    SCREENS.ABOUT-> About()
                }
            }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

enum class SCREENS {
    HOME,
    ABOUT
}