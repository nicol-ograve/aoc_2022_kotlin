package day9

import shared.*

class InputReader {
    fun parseLine(line: String): List<Move> {
        val parts = line.split(" ");
        val move = movementFromChar(line[0])
        return List(parts[1].toInt()) { move }
    }

    private fun movementFromChar(char: Char): Move = when (char) {
        'L' -> Left
        'R' -> Right
        'U' -> Up
        'D' -> Down
        else -> throw RuntimeException("Invalid move")
    }

}