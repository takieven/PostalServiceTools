package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ui.components.Title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Postal() {
    Scaffold(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        topBar = {
            TopAppBar(
                title = { Title("Postal") },
            )
        },
    ) {
        Box(Modifier.fillMaxSize()) {
        }
    }
}