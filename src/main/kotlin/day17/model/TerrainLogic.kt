package day17.model

import day17.services.GasIterator
import day17.services.RockIterator
import shared.*

enum class TerrainBlock {
    Air, RestingRock, MovingRock
}


class TerrainLogic(private val gasIterator: GasIterator, val onRockFallen: (terrain: TerrainLogic) -> Unit) {

    private val grid = ArrayList<Array<TerrainBlock>>()

    private var fallingRock: Rock? = null
    private val rocksProvider = RockIterator()

    val height: Int
        get() = grid.size

    fun nextMove() {
        if (fallingRock == null) {
            fallingRock = rocksProvider.next()
            fallingRock!!.moveTo(Point(2, grid.size + 3))
            executeGasMove()
            executeGasMove()
            executeGasMove()
            fallingRock!!.move(Down, 3)
        } else {
            executeGasMove()

            val mustFreezeFallingRock =
                fallingRock!!.bottomY == 0 || fallingRock!!.bottomMostBlocks.any { this[Down.execute(it.position)] == TerrainBlock.RestingRock }

            if (mustFreezeFallingRock) {
                freezeFallingRock()
            } else {
                fallingRock!!.move(Down)
            }

        }
    }

    private fun executeGasMove() {

        val nextGasMove = gasIterator.next()
        if (canMoveBeExecuted(nextGasMove)) {
            fallingRock!!.move(nextGasMove)
        }
    }

    private fun canMoveBeExecuted(move: Move): Boolean {
        val rock = fallingRock!!
        return when (move) {
            Left -> rock.leftMostX > 0 && rock.leftMostBlocks.all { this[Left.execute(it.position)] == TerrainBlock.Air }
            Right -> rock.rightMostX < 6 && rock.rightMostBlocks.all { this[Right.execute(it.position)] == TerrainBlock.Air }
            else -> throw RuntimeException()
        }
    }

    private fun freezeFallingRock() {
        val rock = fallingRock!!

        while (grid.size <= rock.topY) {
            addEmptyLine()
        }
        rock.blocks.map { it.position }.forEach {
            grid[it.y][it.x] = TerrainBlock.RestingRock
        }


        fallingRock = null
        onRockFallen(this)
    }


    operator fun get(point: Point): TerrainBlock {
        return if (point.y < grid.size) {
            grid[point.y][point.x]
        } else {
            TerrainBlock.Air
        }
    }

    private fun addEmptyLine() = grid.add(
        Array(7) { TerrainBlock.Air }
    )


    /********************* PRINT LOGIC ****************************/
    fun print() {
        val tempGrid = ArrayList<Array<TerrainBlock>>()
        val maxY = if (fallingRock != null) maxOf(fallingRock!!.topY, grid.size - 1) else grid.size - 1

        for (y in 0..maxY) {
            if (y < grid.size) {
                tempGrid.add(Array(7) { grid[y][it] })
            } else {
                tempGrid.add(Array(7) { TerrainBlock.Air })
            }
        }


        val fallingRockPoints = fallingRock?.blocks?.map { it.position }?.toTypedArray() ?: emptyArray<Point>()
        fallingRockPoints.forEach {
            tempGrid[it.y][it.x] = TerrainBlock.MovingRock
        }

        tempGrid.reversed().forEach { printLine(it) }

    }

    private fun printLine(line: Array<TerrainBlock>) {
        print('|')
        line.forEach { print(it.toChar()) }
        print('|')
        println()
    }


    /********************* PATTERNS ****************************/

    fun findPatterns(): Array<Pattern> {
        val patterns = mutableListOf<Pattern>()

        for (i in grid.indices) {
            for (j in i + 1 until grid.size) {
                if (grid[i].isEqualTo(grid[j])) {
                    patterns.add(findPattern(i, j))
                }
            }
        }
        return patterns.toTypedArray()

    }

    fun findPattern(first: Int, second: Int): Pattern {
        var offset = 1
        while (first + offset < second && second + offset < grid.size && grid[first + offset].isEqualTo(grid[second + offset])) {
            offset++
        }
        return Pattern(first, second, offset)
    }


}

fun Array<TerrainBlock>.isEqualTo(other: Array<TerrainBlock>): Boolean {

    for (i in indices) {
        if (this[i] != other[i]) {
            return false
        }
    }
    return true
}


fun TerrainBlock.toChar(): Char {
    return when (this) {
        TerrainBlock.Air -> '.'
        TerrainBlock.RestingRock -> '#'
        TerrainBlock.MovingRock -> '@'
    }
}

