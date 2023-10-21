import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.oned.Code128Writer
import org.jetbrains.skiko.toBitmap
import ui.screens.SerialType

object Utils {
    fun checkDigit(serial: String, mod: Int): Boolean {
        return serial.last().digitToInt() == generateCheckDigit(mod, serial.dropLast(1))
    }

    fun validateISBN(serial: String): Boolean {
        val checkDigit = generateISBNCheckDigit(serial.dropLast(1))
        return checkDigit == try {serial.last().digitToInt()} catch (e: Exception) {false}
    }

    fun generateSerial(digits: Int): String {
        val serial = MutableList(digits) {
            (0..9).random()
        }
        val sum = serial.take(digits-1).sum()
        val mod = digits-2
        serial[serial.lastIndex] = sum%mod
        return serial.joinToString("")
    }

    fun generateCheckDigit(mod: Int, serial: String): Int {
        serial.map { it.digitToInt() }.sum().let {
            return it%mod
        }
    }

    fun generateISBNCheckDigit(serial: String): Int {
        serial.map { it.digitToInt() }.mapIndexed { i, digit ->
            if (i % 2 == 0) {
                digit * 3
            }
            digit
        }.sum().let {
            return 10 - (it % 10)
        }
    }

    fun getType(serial: String): SerialType {
        return when (serial.length) {
            13 -> SerialType.ISBN
            11 -> SerialType.USPS
            else -> SerialType.INVALID
        }
    }

    fun createBarcodeBitmap(input: String): ImageBitmap {
        val matrix = Code128Writer().encode(input, BarcodeFormat.CODE_128, 300, 65)
        return MatrixToImageWriter.toBufferedImage(matrix).toBitmap().asComposeImageBitmap()
    }
}