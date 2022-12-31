package day9.model.rope;

import shared.Move
import shared.Point
import kotlin.math.abs

class MultiKnotsRope(knotsCount: Int) : Rope() {

    override val knots = Array(knotsCount) { Point(0, 0) }

    override var onTailPositionChanged: (() -> Unit)? = null

    override fun move(move: Move) {
        knots[0] = move.execute(knots[0])
        onKnotMoved(0)
    }

    private fun onKnotMoved(movedKnotIndex: Int) {
        if (movedKnotIndex == knots.size - 1) {
            onTailPositionChanged?.invoke()
        } else {

            val movedKnot = knots[movedKnotIndex]
            val nextKnot = knots[movedKnotIndex + 1]

            if (abs(movedKnot.x - nextKnot.x) < 2 && abs(movedKnot.y - nextKnot.y) < 2) {
                return
            }

            val newX =
                if (movedKnot.x == nextKnot.x) movedKnot.x else if (movedKnot.x > nextKnot.x) nextKnot.x + 1 else nextKnot.x - 1
            val newY =
                if (movedKnot.y == nextKnot.y) movedKnot.y else if (movedKnot.y > nextKnot.y) nextKnot.y + 1 else nextKnot.y - 1

            knots[movedKnotIndex + 1] = Point(newX, newY)
            onKnotMoved(movedKnotIndex + 1)
        }
    }

}
