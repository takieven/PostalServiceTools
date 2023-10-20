package ui.screens

import Utils
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    var input by remember { mutableStateOf(Utils.generateSerial(11)) }
    var valid by remember { mutableStateOf(Utils.checkDigit(input)) }
    var type by remember { mutableStateOf(Utils.getType(input)) }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    fun update() {
        type = Utils.getType(input)
        valid = when (type) {
            SerialType.INVALID -> false
            SerialType.ISBN -> Utils.validateISBN(input)
            SerialType.UPC -> TODO()
            SerialType.USPS -> Utils.checkDigit(input)
        }
        println(valid)
        println(type)
    }

    BottomSheetScaffold(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        topBar = {
            TopAppBar(
                title = { Text("Validate a Serial Number", color = MaterialTheme.colorScheme.primary) },
            )
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
        sheetPeekHeight = 100.dp,
        sheetShape = RoundedCornerShape(16.dp),
        sheetContent = {
            Details(input, valid)
        },
        scaffoldState = bottomSheetScaffoldState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(Modifier.align(Alignment.Center).offset(0.dp, (-50).dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box {
                    Image(
                        modifier = Modifier.align(Alignment.TopStart),
                        bitmap = Utils.createBarcodeBitmap(input),
                        contentDescription = "barcode"
                    )
                }
                Spacer(Modifier.height(15.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(.6f),
                        value = input,
                        onValueChange = {
                            input = it
                            update()
                        },
                        placeholder = { Text("Serial number") },
                        isError = !valid,
                        trailingIcon = {
                            when (valid) {
                                true -> Icon(imageVector = Icons.Default.CheckCircle, "check", tint = MaterialTheme.colorScheme.primary)
                                false -> Icon(imageVector = Icons.Default.Warning, "check")
                            }
                        }
                    )
                }

                Spacer(Modifier.height(3.dp))

                Row {
                    Button(onClick = {
                        input = Utils.generateSerial(11)
                        update()
                    },
                        enabled = when (!valid) {false->true; true->false}
                    ) {
                        Text("Generate valid serial")
                    }

                    Spacer(Modifier.width(10.dp))

                    Button(onClick = {
                        when (input.length) {
                            12 -> input += Utils.generateISBNCheckDigit(input)
                            10 -> input += Utils.generateCheckDigit(9, input)
                        }
                        update()
                    },
                        enabled = when (valid) {false->true; true->false}
                    ) {
                        Text("Generate check digit")
                    }
                }
            }
        }
    }
}

@Composable
fun Details(serial: String, valid: Boolean) {
    Column(Modifier.padding(10.dp)) {
        Header("Solution")
        Text("The United States Postal Service (USPS) uses 11-digit serial numbers on its money orders. The first ten digits identify the document, and the last digit is the check digit")
        Divider(Modifier.padding(0.dp, 10.dp))

        if (valid) {
            Text(
                "The money order has serial number a = $serial.The money order is identified by the first 10 digits ${
                    serial.removeSuffix(
                        serial[serial.lastIndex].toString()
                    )
                }. The 11th digit ${serial[serial.lastIndex]} is the check digit."
            )
                val solution = StringBuilder()

            serial.forEachIndexed { i, digit ->
                if (i < serial.lastIndex) {
                    solution.append(digit)
                    if (i < serial.lastIndex - 1) {
                        solution.append(" + ")
                    }
                }
            }
                val sum = serial.toList().map { it.digitToInt() }.take(10).sum()

            solution.appendLine(" = $sum")
                    solution . append ("$sum mod 9 = ${sum % 9}")
                    Text (solution.toString())
        }
    }
}

@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = text, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.tertiary)
}

enum class SerialType {
    ISBN,
    UPC,
    USPS,
    INVALID
}