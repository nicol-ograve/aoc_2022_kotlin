package day9;

import shared.Point
import day9.model.rope.Rope

public class TailPositionsCounter(val rope: Rope) {

    val visitedCellsCount: Int
        get() = visitedCells.size

    private val visitedCells = HashSet<Point>()

    init {
        rope.onTailPositionChanged = this::onRopeTailPositionChanged
        visitedCells.add(rope.tail)
    }

    private fun onRopeTailPositionChanged() {
        if(!visitedCells.contains(rope.tail)){
            visitedCells.add(rope.tail)
        }
    }

}

private class VisitedCell {

    var visitedMultipleTimes = false
        private set

    fun onVisitedAgain() {
        if (!visitedMultipleTimes) {
            visitedMultipleTimes = true
        }
    }

}