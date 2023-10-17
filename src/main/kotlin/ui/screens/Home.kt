package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Home() {
    var input by remember { mutableStateOf(Utils.generateSerial(11)) }
    var valid by remember { mutableStateOf(Utils.checkDigit(input)) }

    Box(modifier = Modifier.fillMaxSize().offset(0.dp, 0.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Validate a Serial Number", style = MaterialTheme.typography.h4, color = MaterialTheme.colors.secondaryVariant)
            Spacer(Modifier.height(15.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = input,
                    onValueChange = {
                        input = it
                        valid = Utils.checkDigit(input)
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
                    valid = Utils.checkDigit(input)
                },
                    enabled = when (!valid) {false->true; true->false}
                ) {
                    Text("Generate valid serial")
                }

                Spacer(Modifier.width(10.dp))

                Button(onClick = {
                    input = Utils.generateCheckDigit(serial = input)
                    valid = Utils.checkDigit(input)
                },
                    enabled = when (valid) {false->true; true->false}
                ) {
                    Text("Generate check digit")
                }
            }
        }
    }
}