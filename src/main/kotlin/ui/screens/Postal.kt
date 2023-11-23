package ui.screens

import MainViewModel
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import logic.Utils
import ui.components.Title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Postal(viewModel: MainViewModel) {
    val state by viewModel.postalState.collectAsState()

    Scaffold(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        content = {
                Column(Modifier.padding(top = it.calculateTopPadding(), bottom = 12.dp).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = state.search,
                        onValueChange = { txt ->
                            viewModel.updateSearch(txt)
                        },
                        label = { Text("Search") },
                        leadingIcon = { Icon(Icons.Default.Search, "Search") },
                        modifier = Modifier.fillMaxWidth(.7f))

                    val list = if (state.search.isNotEmpty()) state.zips.filter { code ->
                        (code.province+" "+code.barangay+" "+code.code).lowercase().contains(state.search.lowercase())
                    } else state.zips

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        this.items(items = list) { code ->
                            ZipItem(code)
                        }
                    }
                }
        },
        topBar = {
            TopAppBar(
                title = { Title("Postal") },
            )
        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun ZipItem(postalCode: PostalCode) {
    Card(Modifier.fillMaxWidth(.8f).onClick {
        Utils.setClipboard(postalCode.code)
    }) {
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = postalCode.province, Modifier.weight(.3f))
            Text(text = postalCode.barangay, Modifier.weight(.4f))
            Text(text = postalCode.code)
        }
    }
}

data class PostalCode(
    val province: String,
    val barangay: String,
    val code: String
)