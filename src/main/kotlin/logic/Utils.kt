package logic

import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.Code128Writer
import ui.screens.PostalCode
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.io.InputStream

object Utils {
    fun checkDigit(serial: String, mod: Int): Boolean {
        if (serial.isEmpty()) return false
        if (serial.length != 11) return false

        return serial.last().digitToInt() == generateCheckDigit(mod, serial.dropLast(1))
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

    fun createBarcodeBitmap(input: String): BitMatrix? {
        return try {
            Code128Writer().encode(input, BarcodeFormat.CODE_128, 250, 65)
        } catch (e: Exception) {
            null
        }
    }

    fun findMissingDigit(input: String): String {
        input.find { it == ' ' }?.let {
            val mod = 9
            for (i in 0..9) {
                val serial = input.replace(" ", i.toString())
                if (checkDigit(serial, mod)) {
                    return serial
                }
            }
        }
        return "No valid serial found"
    }

    fun readCsv(inputStream: InputStream): List<PostalCode> {
        val reader = inputStream.bufferedReader()
        return reader.lineSequence().map {
            val (province, barangay, code) = it.split(",")
            PostalCode(province, barangay, code)
        }.toList()
    }

    fun setClipboard(s: String) {
        val selection = StringSelection(s)
        val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(selection, selection)
    }
}