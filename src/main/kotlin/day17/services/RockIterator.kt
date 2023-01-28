package day17.services

import day17.model.*

class RockIterator : Iterator<Rock> {
    private var currentIndex = 0

    override fun hasNext(): Boolean = true

    override fun next(): Rock {
        val rock = when (currentIndex) {
            0 -> HorizontalRock()
            1 -> CrossRock()
            2 -> ReversedLRock()
            3 -> VerticalRock()
            4 -> SquareRock()
            else -> throw RuntimeException()
        }

        currentIndex = if (currentIndex == 4) 0 else currentIndex + 1

        return rock
    }
}