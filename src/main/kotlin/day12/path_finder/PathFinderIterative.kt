package day12.path_finder

import day12.moves
import shared.Point
import java.util.Deque
import java.util.LinkedList
import shared.*

class PathFinderIterative(private val matrix: Array<Array<Int>>, val destination: Point) : PathFinder {

    private val minPaths: Array<Array<Int?>> = Array(matrix.size) {
        Array(matrix[0].size) { null }
    }

    override fun findMinPathLength(startPoint: Point): Int? {
        return minPathIterative(arrayOf(startPoint))
    }

    override fun findMinPathLength(startingPoints: Array<Point>): Int? {
        return minPathIterative(startingPoints)
    }

    private fun minPathIterative(entryPoints: Array<Point>): Int? {

        val points: Deque<PathPoint> = LinkedList()

        entryPoints.forEach {
            points.add(PathPoint(it, 0))
            minPaths[it] = 0

        }


        while (points.isNotEmpty()) {
            val currentPoint = points.pollFirst()

            if (currentPoint.point.x != destination.x || currentPoint.point.y != destination.y) {

                moves.forEach { move ->
                    val nextPoint = move.execute(currentPoint.point)

                    if (isPointReachable(nextPoint, currentPoint.point)) {

                        if (minPaths[nextPoint] != null) {
                            if (minPaths[nextPoint]!! > currentPoint.cost + 1) {
                                minPaths[nextPoint] = currentPoint.cost + 1
                            }
                        } else {
                            minPaths[nextPoint] = currentPoint.cost + 1
                            points.addLast(PathPoint(nextPoint, currentPoint.cost + 1))
                        }
                    }

                }
            } else {
                println("Reacheeed")
            }
        }

        return minPaths[destination]

    }

    private fun isPointReachable(point: Point, from: Point): Boolean {
        return matrix.contains(point) && matrix[point] <= matrix[from] + 1
    }

}


data class PathPoint(val point: Point, var cost: Int)