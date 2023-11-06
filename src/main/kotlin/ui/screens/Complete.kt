package ui.screens

import logic.Utils
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Complete() {
    BottomSheetScaffold(
        modifier = Modifier.clip(RoundedCornerShape(16.dp, 0.dp, 0.dp)),
        topBar = {
            TopAppBar(
                title = { Text("Find Missing Digit", color = MaterialTheme.colorScheme.primary) },
            )
        },
        sheetContent = {
                       Solution("")
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
            ) {
                DigitTextField()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DigitTextField() {
    var value by remember { mutableStateOf("           ") }
    var valid by remember { mutableStateOf(false) }
    var complete by remember { mutableStateOf("") }
    var index = 0

    Column(Modifier.padding(10.dp).offset(y=(-30).dp)) {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            value.forEachIndexed { i, it ->
                OutlinedTextField(modifier = Modifier.width(45.dp).padding(top = 10.dp), value = it.toString(), onValueChange = { char ->
                    if (char.length>=11) {
                        value = char.filter { it.isDigit() }
                    }
                    if (char.isEmpty()) {
                        value = value.replaceRange(i, i + 1, " ")
                    }
                    if (char.length<=2&&char.contains(" ")) {
                        if (char.isEmpty()) {
                            value = value.replaceRange(i, i + 1, " ")
                        } else {
                            char.filter { it.isDigit() }.ifEmpty { value = value.replaceRange(i, i + 1, " "); return@OutlinedTextField }
                            value = value.replaceRange(i, i + 1, char.filter { it.isDigit() })
                        }
                    }

                    valid = value.count { it == ' ' } == 1 && value.last() != ' '

                }, textStyle = MaterialTheme.typography.headlineSmall)
            }
            IconButton(onClick = {
                Utils.setClipboard(value)
            }) {
                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "copy")
            }
        }
        Spacer(Modifier.height(10.dp))
        Button(onClick = {
            complete = Utils.findMissingDigit(value)
            index = value.indexOf(' ')
        }, enabled = valid) {
            Text("Complete")
        }
        Spacer(Modifier.height(10.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                complete.forEachIndexed { i, it ->
                    if (index == i) {
                        OutlinedTextField(
                            modifier = Modifier.width(45.dp).padding(top = 10.dp),
                            value = it.toString(),
                            onValueChange = {},
                            textStyle = MaterialTheme.typography.headlineSmall, enabled = false,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                disabledBorderColor = MaterialTheme.colorScheme.primary,
                                disabledTextColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    } else {
                        OutlinedTextField(
                            modifier = Modifier.width(45.dp).padding(top = 10.dp),
                            value = it.toString(),
                            onValueChange = {},
                            textStyle = MaterialTheme.typography.headlineSmall, enabled = false,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
                if (complete.isNotEmpty()) {
                    IconButton(onClick = {
                        Utils.setClipboard(complete)
                    }) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "copy")
                    }
            }
        }
    }
}
@Composable
fun Solution(serial: String) {
    Column(Modifier.padding(10.dp)) {
        Header("Solution")
    }
}