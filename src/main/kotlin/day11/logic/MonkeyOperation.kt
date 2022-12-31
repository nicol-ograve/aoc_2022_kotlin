package day11.logic

class MonkeyOperation(private val operand: Long?, private val operator: String) {

    fun execute(value: Long): Long {

        if (operand == null) {
            return value * value
        }

        return when (operator) {
            "+" -> value + operand
            "*" -> value * operand
            "-" -> value - operand
            else -> value / operand
        }
    }
}