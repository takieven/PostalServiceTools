object Utils {
    fun checkDigit(mod: Int, serial: String): Boolean {
        var sum = 0
        return try {
            for (i in 0 until serial.lastIndex) {
                sum += serial[i].digitToInt()
            }
            sum%mod == serial.last().digitToInt()
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
}