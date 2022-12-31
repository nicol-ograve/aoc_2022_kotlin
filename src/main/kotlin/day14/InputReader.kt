package day14

import day14.model.AirTile
import day14.model.CaveTile
import day14.model.RockBlock
import day14.model.SandSource
import shared.Matrix
import shared.Point
import shared.pointsBetween

fun readInput(lines: List<String>): Matrix<CaveTile> {

    var minX = Int.MAX_VALUE

    var maxX = -1
    var maxY = -1

    val blocks = hashSetOf<Point>()

    lines.forEach { line ->
        val vertexes = line.split(" -> ").map { rockVertex ->
            val coordinates = rockVertex.split(",").map { it.toInt() }
            val point = Point(coordinates[0], coordinates[1])

            when {
                point.x > maxX -> maxX = point.x
                point.y > maxY -> maxY = point.y
                point.x < minX -> minX = point.x
            }

            point
        }

        for (i in 1 until vertexes.size) {
            blocks.addAll(pointsBetween(vertexes[i - 1], vertexes[i]))
        }
    }


    val matrix: Matrix<CaveTile> = Array(maxY + 3) { y ->
        Array(maxX - minX + 1001) { offsetX ->
            val x = minX + offsetX - 500
            val point = Point(x, y)
            when {
                y == maxY + 1 -> AirTile(point)
                y == maxY + 2 -> RockBlock(point)
                blocks.contains(point) -> RockBlock(point)
                x == 500 && y == 0 -> SandSource(point)
                else -> AirTile(point)
            }
        }
    }
    return matrix
}