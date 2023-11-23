import androidx.compose.ui.res.useResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import logic.Utils
import logic.Utils.readCsv
import ui.screens.PostalCode

class MainViewModel {
    val input = MutableStateFlow("")
    val valid = MutableStateFlow(Utils.checkDigit(input.value, 9))
    val digitState = MutableStateFlow(DigitState())

    fun updateInput(newInput: String) {
        input.value = newInput
        valid.value = Utils.checkDigit(input.value, 9)
    }

    private fun updateValue(text: String) {
        digitState.update {
            it.copy(value = text)
        }
    }

    fun updateDigit(char: String, i: Int) {
        if (char.length>=11) {
            updateValue(char.filter { it.isDigit() })
        }
        if (char.isEmpty()) {
            updateValue(digitState.value.value.replaceRange(i, i + 1, " "))
        }
        if (char.length<=2&&char.contains(" ")) {
            if (char.isEmpty()) {
                updateValue(digitState.value.value.replaceRange(i, i + 1, " "))
            } else {
                char.filter { it.isDigit() }.ifEmpty { updateValue(digitState.value.value.replaceRange(i, i + 1, " ")); return}
                updateValue(digitState.value.value.replaceRange(i, i + 1, char.filter { it.isDigit() }))
            }
        }

        digitState.update { state ->
            state.copy(valid = state.value.count { it == ' ' } == 1 && state.value.last() != ' ')
        }
    }

    fun complete() {
        digitState.update {
            it.copy(
                complete = Utils.findMissingDigit(it.value),
                index = it.value.indexOf(' ')
            )
        }
    }

    // Postal
    val postalState = MutableStateFlow(PostalState())

    fun updateSearch(text: String) {
        postalState.update {
            it.copy(search = text)
        }
    }

    init {
        useResource("zips.csv") {stream ->
            val zips: MutableList<PostalCode> = mutableListOf()
            readCsv(stream).forEach { shit ->
                zips.add(shit)
            }
            postalState.value = PostalState(zips)
        }
    }
}

data class DigitState(
    val value: String = "           ",
    val valid: Boolean = false,
    val complete: String = "           ",
    val index: Int = 0
)

data class PostalState(
    val zips: List<PostalCode> = listOf(),
    val search: String = ""
)