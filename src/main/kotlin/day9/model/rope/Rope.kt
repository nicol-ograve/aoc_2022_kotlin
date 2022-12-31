package day9.model.rope

import shared.Move
import shared.Point

abstract class Rope {
    abstract val knots: Array<Point>
    abstract var onTailPositionChanged: (() -> Unit)?
    abstract fun move(move: Move)

    val head: Point
        get() = knots[0]

    val tail: Point
        get() = knots[knots.size - 1]
}