package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About() {
    Scaffold(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        topBar = {
            TopAppBar(
                title = { Text("About", color = MaterialTheme.colorScheme.primary) },
            )
        },
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                Card(Modifier.fillMaxWidth(.8f)) {
                    Column(Modifier.padding(30.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Header(text = "Group 6")
                        Spacer(Modifier.height(10.dp))
                        Column {
                            Profile("Steven Miranda", Icons.Default.AccountBox)
                            Profile("Rustan Saquing", Icons.Default.AccountBox)
                            Profile("Jeferson Paguila", Icons.Default.AccountBox)
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
                Card(Modifier.fillMaxWidth(.8f)) {
                    Column(Modifier.padding(30.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Header(text = "Libraries Used")
                        Spacer(Modifier.height(10.dp))
                        Column {
                            Text("qr code generator: com.google.zxing:javase:3.5.2")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Profile(name: String, icon: ImageVector) {
    Row {
        Image(imageVector = icon, contentDescription = "profile")
        Spacer(Modifier.width(10.dp))
        Text(name)
    }
}