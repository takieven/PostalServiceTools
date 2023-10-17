import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

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

@Composable
fun Home() {
    var input by remember { mutableStateOf(Utils.generateSerial(11)) }
    var valid by remember { mutableStateOf(checkDigit(input)) }

    Box(modifier = Modifier.fillMaxSize().offset(0.dp, 0.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Validate a Serial Number", style = MaterialTheme.typography.h4, color = MaterialTheme.colors.secondaryVariant)
            Spacer(Modifier.height(15.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = input,
                    onValueChange = {
                        input = it
                        valid = checkDigit(input)
                    },
                    placeholder = { Text("Serial number") },
                    isError = !valid,
                    trailingIcon = {
                        when (valid) {
                            true -> Icon(imageVector = Icons.Default.CheckCircle, "check", tint = MaterialTheme.colors.primary)
                            false -> Icon(imageVector = Icons.Default.Warning, "check")
                        }
                    }
                )
            }

            Spacer(Modifier.height(3.dp))

            Row {
                Button(onClick = {
                    input = Utils.generateSerial(11)
                    valid = checkDigit(input)
                },
                   enabled = when (!valid) {false->true; true->false}
                    ) {
                    Text("Generate valid serial")
                }

                Spacer(Modifier.width(10.dp))

                Button(onClick = {
                    input = Utils.generateCheckDigit(serial = input)
                    valid = checkDigit(input)
                },
                    enabled = when (valid) {false->true; true->false}
                    ) {
                    Text("Generate check digit")
                }
            }
        }
    }
}

@Composable
fun Currency() {
    Box(Modifier.fillMaxSize()) {}
}

fun checkDigit(serial: String): Boolean {
    return Utils.checkDigit(serial)
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