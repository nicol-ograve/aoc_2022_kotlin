package day17.services

import shared.Left
import shared.Move
import shared.Right

class GasIterator(private val sequence: String) : Iterator<Move> {
    private var charIndex = 0
    override fun hasNext(): Boolean = true

    override fun next(): Move {
        val nextChar = sequence[charIndex]
        val nextMove = if (nextChar == '>') Right else Left

        charIndex = if (charIndex == sequence.length - 1) 0 else charIndex + 1


        return nextMove
    }
}