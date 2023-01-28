package day17.model

import shared.Move
import shared.Point

class RockBlock(x: Int, y: Int) {
    var position = Point(x, y)
    override fun toString(): String {
        return position.toString()
    }
}

sealed class Rock() {
    private var currentOrigin = Point(0, 0)
    abstract val blocks: Array<RockBlock>
    abstract val height: Int
    abstract val width: Int

    abstract val topY: Int
    abstract val bottomY: Int
    abstract val leftMostX: Int
    abstract val rightMostX: Int

    abstract val bottomMostBlocks: Array<RockBlock>
    abstract val leftMostBlocks: Array<RockBlock>
    abstract val rightMostBlocks: Array<RockBlock>

    fun moveTo(newOrigin: Point) {
        blocks.forEach {
            it.position =
                Point(it.position.x - currentOrigin.x + newOrigin.x, it.position.y - currentOrigin.y + newOrigin.y)
        }
        currentOrigin = newOrigin
    }

    fun move(move: Move, times:Int = 1) {
        moveTo(move.execute(currentOrigin, times))
    }

}

class HorizontalRock() : Rock() {
    override val blocks = arrayOf(RockBlock(0, 0), RockBlock(1, 0), RockBlock(2, 0), RockBlock(3, 0))
    override val height: Int = 1
    override val width: Int = 4
    override val topY: Int
        get() = blocks[0].position.y
    override val bottomY: Int
        get() = blocks[0].position.y
    override val leftMostX: Int
        get() = blocks[0].position.x
    override val rightMostX: Int
        get() = blocks[3].position.x
    override val bottomMostBlocks = arrayOf(blocks[0], blocks[1], blocks[2], blocks[3])
    override val leftMostBlocks = arrayOf(blocks[0])
    override val rightMostBlocks = arrayOf(blocks[3])

}

class SquareRock() : Rock() {
    override val blocks = arrayOf(RockBlock(0, 0), RockBlock(1, 0), RockBlock(1, 1), RockBlock(0, 1))
    override val height = 2
    override val width = 2
    override val topY: Int
        get() = blocks[2].position.y
    override val bottomY: Int
        get() = blocks[0].position.y
    override val leftMostX: Int
        get() = blocks[0].position.x
    override val rightMostX: Int
        get() = blocks[1].position.x
    override val bottomMostBlocks = arrayOf(blocks[0], blocks[1])
    override val leftMostBlocks = arrayOf(blocks[0], blocks[3])
    override val rightMostBlocks = arrayOf(blocks[1], blocks[2])
}

class VerticalRock() : Rock() {
    override val blocks = arrayOf(RockBlock(0, 0), RockBlock(0, 1), RockBlock(0, 2), RockBlock(0, 3))
    override val height = 4
    override val width = 1
    override val topY: Int
        get() = blocks[3].position.y
    override val bottomY: Int
        get() = blocks[0].position.y
    override val leftMostX: Int
        get() = blocks[0].position.x
    override val rightMostX: Int
        get() = blocks[0].position.x
    override val bottomMostBlocks = arrayOf(blocks[0])
    override val leftMostBlocks = arrayOf(blocks[0], blocks[1], blocks[2], blocks[3])
    override val rightMostBlocks = arrayOf(blocks[0], blocks[1], blocks[2], blocks[3])
}

class ReversedLRock() : Rock() {
    override val blocks = arrayOf(RockBlock(0, 0), RockBlock(1, 0), RockBlock(2, 0), RockBlock(2, 1), RockBlock(2, 2))
    override val height = 3
    override val width = 3
    override val topY: Int
        get() = blocks[4].position.y
    override val bottomY: Int
        get() = blocks[0].position.y
    override val leftMostX: Int
        get() = blocks[0].position.x
    override val rightMostX: Int
        get() = blocks[2].position.x
    override val bottomMostBlocks = arrayOf(blocks[0], blocks[1], blocks[2])
    override val leftMostBlocks = arrayOf(blocks[0], blocks[3], blocks[4])
    override val rightMostBlocks = arrayOf(blocks[2], blocks[3], blocks[4])
}

class CrossRock() : Rock() {
    override val blocks = arrayOf(RockBlock(0, 1), RockBlock(1, 2), RockBlock(1, 1), RockBlock(1, 0), RockBlock(2, 1))
    override val height = 3
    override val width = 3
    override val topY: Int
        get() = blocks[1].position.y
    override val bottomY: Int
        get() = blocks[3].position.y
    override val leftMostX: Int
        get() = blocks[0].position.x
    override val rightMostX: Int
        get() = blocks[4].position.x
    override val bottomMostBlocks = arrayOf(blocks[0], blocks[3], blocks[4])
    override val leftMostBlocks = arrayOf(blocks[0], blocks[1], blocks[3])
    override val rightMostBlocks = arrayOf(blocks[4], blocks[1], blocks[3])
}

