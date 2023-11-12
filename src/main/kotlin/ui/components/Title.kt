package ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Title(text: String) {
    Text(
        text,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}