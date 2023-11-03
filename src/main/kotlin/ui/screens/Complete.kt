package ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Complete() {
    var value by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.clip(RoundedCornerShape(16.dp, 0.dp, 0.dp)),
        topBar = {
            TopAppBar(
                title = { Text("Find Missing Digit", color = MaterialTheme.colorScheme.primary) },
            )
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
    ) { pad ->
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
            ) {
                DigitTextField()
            }
        }
    }
}

@Composable
@Preview
fun DigitTextField() {
    var value by remember { mutableStateOf("") }
    var valid by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        value = "xxxxxxxxxxx"
    }

    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            value.forEachIndexed { i, it ->
                OutlinedTextField(modifier = Modifier.width(45.dp), value = it.toString(), onValueChange = { char ->
                    if (char.isEmpty()) {
                        value = value.replaceRange(i, i + 1, "x")
                    }
                    if (char.length<=2&&char.contains("x")) {
                        if (char.isEmpty()) {
                            value = value.replaceRange(i, i + 1, "x")
                        } else {
                            char.filter { it.isDigit() }.ifEmpty { value = value.replaceRange(i, i + 1, "x"); return@OutlinedTextField }
                            value = value.replaceRange(i, i + 1, char.filter { it.isDigit() })
                        }
                    }

                    valid = value.count { it == 'x' } == 1 && value.last() != 'x'

                }, textStyle = MaterialTheme.typography.headlineSmall)
            }
        }
        Spacer(Modifier.height(10.dp))
        Button(onClick = {}, enabled = valid) {
            Text("Complete")
        }
        Spacer(Modifier.height(10.dp))
        Text("Valid: $valid")
    }
}