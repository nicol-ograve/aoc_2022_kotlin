package day12

import day12.path_finder.PathFinder
import day12.path_finder.PathFinderIterative
import day9.model.*
import shared.*
import utils.getDataLines

const val startPointHeight = 1
const val destinationHeight = 27


fun main(args: Array<String>) {
    val lines = getDataLines(12, arrayOf("dejmo"))

    var startingPoints: MutableList<Point> = mutableListOf()
    var destination: Point? = null

    val matrix = Array(lines.size) { row ->
        val line = lines[row]
        Array(line.length) { column ->
            var height = line[column].height()
            when (height) {
                startPointHeight -> {
                    startingPoints.add(Point(row, column))
                }
                destinationHeight -> {
                    height -= 1
                    destination = Point(row, column)
                }
            }
            height
        }
    }

    val finder: PathFinder = PathFinderIterative(matrix, destination!!)
    val result = finder.findMinPathLength(startingPoints.toTypedArray())


    println(result)
}

val moves = arrayOf(Up, Left, Down, Right)


fun Char.height(): Int {
    if (this == 'S') {
        return startPointHeight
    } else if (this == 'E') {
        return destinationHeight
    }
    return code.toByte().toInt() - 'a'.code.toByte().toInt() + 1
}