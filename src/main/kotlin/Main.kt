import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.screens.Currency
import ui.screens.Home

@Composable
@Preview
fun App() {
    var screen by remember { mutableStateOf(SCREENS.HOME) }

    MaterialTheme {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                IconButton(onClick = {
                    screen = SCREENS.HOME
                }) {
                    Icon(imageVector = Icons.Default.Home, "home")
                }
                IconButton(onClick = {
                    screen = SCREENS.CURRENCY
                }) {
                    Icon(imageVector = Icons.Default.AccountBox, "home")
                }
            }

            when (screen) {
                SCREENS.HOME -> Home()
                SCREENS.CURRENCY -> Currency()
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
    CURRENCY
}