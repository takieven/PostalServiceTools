import ui.screens.SerialType

object Utils {
    fun checkDigit(serial: String, mod: Int = 9): Boolean {
        var sum = 0
        return try {
            for (i in 0 until serial.lastIndex) {
                sum += serial[i].digitToInt()
            }
            println("sum: $sum, mod: $mod, 56mod11: ${sum%mod}")
            sum%mod == serial.last().digitToInt()
        } catch (e: Exception) {
            false
        }
    }

    fun validateISBN(serial: String): Boolean {
        val checkDigit = generateISBNCheckDigit(serial.dropLast(1))
        return checkDigit == serial.last().digitToInt()
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

    fun generateCheckDigit(mod: Int = 9, serial: String): Int {
        var sum = 0
            for (i in 0..serial.lastIndex) {
                sum += serial[i].digitToInt()
            }
        return sum%mod
    }

    fun generateISBNCheckDigit(serial: String): Int {
        var sum = 0
        for (i in 0..serial.lastIndex) {
            var digit = serial[i].digitToInt()
            if (i%2==0) {
                digit *= 3
            }
            sum += digit
        }
        return 10-(sum%10)
    }

    fun getType(serial: String): SerialType {
        return when (serial.length) {
            13 -> SerialType.ISBN
            11 -> SerialType.USPS
            else -> SerialType.INVALID
        }
    }
}