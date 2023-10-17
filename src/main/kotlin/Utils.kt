object Utils {
    fun checkDigit(serial: String): Boolean {
        var sum = 0
        if (serial.length!=11) {
            return false
        }
        return try {
            for (i in 0 until serial.lastIndex) {
                sum += serial[i].digitToInt()
            }
            sum%(serial.length-2) == serial.last().digitToInt()
        } catch (e: Exception) {
            false
        }
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

    fun generateCheckDigit(mod: Int = 9, serial: String): String {
        var sum = 0
            for (i in 0..serial.lastIndex) {
                sum += serial[i].digitToInt()
            }
        val checkDigit = sum%mod

        return serial+checkDigit
    }
}