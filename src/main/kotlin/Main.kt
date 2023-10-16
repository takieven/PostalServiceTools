import androidx.compose.animation.expandHorizontally
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var input by remember { mutableStateOf(Utils.generateSerial(11)) }
    var valid by remember { mutableStateOf(checkDigit(input)) }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().offset(0.dp, -50.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Validate a serial number", style = MaterialTheme.typography.h4, color = MaterialTheme.colors.secondaryVariant)
                Spacer(Modifier.height(25.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = input,
                        onValueChange = {
                            input = it
                            valid = checkDigit(input)
                        },
                        placeholder = { Text("Serial number") },
                        isError = !checkDigit(input),
                        trailingIcon = {
                            when (checkDigit(input)) {
                                true -> Icon(imageVector = Icons.Default.CheckCircle, "check", tint = MaterialTheme.colors.primary)
                                false -> Icon(imageVector = Icons.Default.Warning, "check")
                            }
                        }
                    )
                }

                Spacer(Modifier.height(10.dp))

                Button(onClick = {
                    input = Utils.generateSerial(11)
                }) {
                    Text("Generate valid serial")
                }
            }
        }
    }
}

fun checkDigit(serial: String): Boolean {
    return Utils.checkDigit(serial.length-2, serial)
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}