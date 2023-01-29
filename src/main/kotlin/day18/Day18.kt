package day18

import day18.model.Point3D
import utils.getDataLines
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
//    val isDemo = true
    val isDemo = false
    val input = getDataLines(18, if (isDemo) arrayOf("demo") else emptyArray())

    val points = readInput(input)


    val result1 = part1(points)
    val result2 = Logic(points).execute()

    println(result1 - result2)
}


fun part1(points: Array<Point3D>): Int {

    var neighborsCount = 0
    for (i in points.indices) {
        val point = points[i]
        for (j in i + 1 until points.size) {
            if (point.neighborOf(points[j])) {
                neighborsCount++
            }
        }
    }

    return points.size * 6 - neighborsCount * 2
}