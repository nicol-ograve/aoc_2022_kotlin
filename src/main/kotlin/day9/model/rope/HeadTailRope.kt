package day9.model.rope

import shared.Move
import shared.Point
import kotlin.math.abs

class HeadTailRope : Rope() {

    private var _head
        private set(value) {
            knots[0] = value
        }
        get() = knots[0]

    private var _tail
        private set(value) {
            knots[1] = value
            onTailPositionChanged?.invoke()
        }
        get() = knots[1]

    override val knots: Array<Point> = arrayOf(Point(0, 0), Point(0, 0))

    override var onTailPositionChanged: (() -> Unit)? = null

    override fun move(move: Move) {
        _head = move.execute(head)
        updateTailPosition()
    }

    private fun updateTailPosition() {
        if (abs(head.x - tail.x) < 2 && abs(head.y - tail.y) < 2) {
            return
        }

        val newX = if (head.x == tail.x) head.x else if (head.x > tail.x) tail.x + 1 else tail.x - 1
        val newY = if (head.y == tail.y) head.y else if (head.y > tail.y) tail.y + 1 else tail.y - 1

        _tail = Point(newX, newY)
    }

}