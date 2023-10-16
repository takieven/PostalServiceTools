package ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextField() {
    var textInput by remember { mutableStateOf("")}
    TextField(value = textInput, onValueChange = {textInput = it},
        modifier = Modifier.fillMaxWidth().padding(10.dp))
}