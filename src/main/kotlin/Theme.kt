import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font

private val appFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resource = "fonts/Roboto-Regular.ttf",
        ),
        Font(
            resource = "fonts/Roboto-Medium.ttf",
        ),
        Font(
            resource = "fonts/Roboto-Bold.ttf",
        ),
    )
)

@Composable
fun CheckDigitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = Typography(
            body1 = Typography().body1.copy(
                fontFamily = appFontFamily
            ),
        )
    ) { content() }
}
