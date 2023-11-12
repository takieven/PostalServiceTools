package ui.screens

import MainViewModel
import logic.Utils
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.client.j2se.MatrixToImageWriter
import org.jetbrains.skiko.toBitmap
import ui.components.AlertDialogExample
import ui.components.Title
import java.nio.file.Paths

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Home(viewModel: MainViewModel) {
    val input by viewModel.input.collectAsState()
    val valid by viewModel.valid.collectAsState()
    var showHelp by remember { mutableStateOf(false) }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        modifier = Modifier.clip(RoundedCornerShape(16.dp, 0.dp, 0.dp)),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(end = 10.dp),
                title = { Title("Validate a serial number") },
                actions = {
                    IconButton(onClick = {
                        showHelp = !showHelp
                    }) {
                        Icon(imageVector = Icons.Outlined.Help, contentDescription = null)
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
        sheetPeekHeight = 100.dp,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp),
        sheetContent = {
            Details(input, valid)
        },
        scaffoldState = bottomSheetScaffoldState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            if (showHelp) {
                AlertDialogExample(
                    modifier = Modifier.fillMaxWidth(.6f),
                    onDismissRequest = { showHelp = false },
                    onConfirmation = {
                        showHelp = false
                    },
                    dialogTitle = "Help",
                    content = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Download, contentDescription = null)
                            Spacer(Modifier.width(10.dp))
                            Text("Click the download button besides the barcode to extract the barcode.png file to your downloads folder.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                )
            }

            Column(
                Modifier.align(Alignment.Center).offset(0.dp, (-50).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                // Barcode stuff
                Box {
                    Utils.createBarcodeBitmap(input)?.let { matrix ->
                        MatrixToImageWriter.toBufferedImage(matrix).toBitmap().asComposeImageBitmap().let {
                            Row(
                                modifier = Modifier.align(Alignment.TopStart),
                            ) {
                                Image(
                                    bitmap = it,
                                    contentDescription = "barcode"
                                )
                                IconButton(onClick = {
                                    val downloadsFolder = "C:/Users/" + System.getProperty("user.name") + "/Downloads/"
                                    MatrixToImageWriter.writeToPath(
                                        matrix,
                                        "png",
                                        Paths.get(downloadsFolder, "barcode.png")
                                    )
                                }) {
                                    Icon(imageVector = Icons.Default.Download, "details")
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(30.dp))

                // Input Textfield
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        label = { Text("Serial number") },
                        modifier = Modifier.fillMaxWidth(.6f),
                        value = input,
                        onValueChange = {
                            viewModel.updateInput(it.filter { digit -> digit.isDigit() })
                        },
                        isError = !valid,
                        trailingIcon = {
                            when (valid) {
                                true -> Icon(imageVector = Icons.Default.CheckCircle, "check", tint = MaterialTheme.colorScheme.primary)
                                false -> Icon(imageVector = Icons.Default.Warning, "check")
                            }
                        }
                    )
                }

                Spacer(Modifier.height(20.dp))

                // The lower buttons
                FlowRow(Modifier.padding(10.dp, 0.dp)) {
                    Button(onClick = {
                        viewModel.updateInput(Utils.generateSerial(11))
                    },
//                        enabled = when (!valid) {false->true; true->false}
                    ) {
                        Text("Generate valid serial")
                    }

                    Spacer(Modifier.width(10.dp))

                    Button(onClick = {
                        viewModel.updateInput(input+Utils.generateCheckDigit(9, input))
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

// Solution BottomSheet
@Composable
fun Details(serial: String, valid: Boolean) {
    Column(Modifier.padding(10.dp)) {
        Header("Solution")
        SelectionContainer {
            Column {
                Text("The United States Postal Service (USPS) uses 11-digit serial numbers on its money orders. The first ten digits identify the document, and the last digit is the check digit")
                Divider(Modifier.padding(0.dp, 10.dp))

                // We only show the solution if we have a valid serial
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
                    solution.append("$sum mod 9 = ${sum % 9}")
                    Text(solution.toString())
                }
            }
        }
    }
}

@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = text, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.tertiary)
}