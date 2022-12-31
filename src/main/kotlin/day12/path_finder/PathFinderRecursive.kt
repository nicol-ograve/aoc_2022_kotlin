package day12.path_finder

import day12.moves
import shared.Move
import shared.*
import utils.printMatrix

class PathFinderRecursive(val matrix: Array<Array<Int>>, val destination: Point): PathFinder {

    private val visited = Array(matrix.size) {
        Array(matrix[0].size) { false }
    }

    private val minPathsCache: Array<Array<Int?>> = Array(matrix.size) {
        Array(matrix[0].size) { null }
    }

    override fun findMinPathLength(startPoint: Point): Int? {
        val path = minPathRecursive(startPoint, null)
        printMatrix(minPathsCache)
        return path
    }

    override fun findMinPathLength(startingPoints: Array<Point>): Int? {
        TODO("Not yet implemented")
    }

    private fun minPathRecursive(point: Point, fromDirection: Move?): Int? {


        visited[point.x][point.y] = true

        if (point.x == destination.x && point.y == destination.y) {
            return 0
        }

        val currentHeight = matrix[point]

        val pointCost = moves.filter { it != fromDirection }.mapNotNull { move ->
            val nextPoint = move.execute(point)

            var cachedNextPointValue: Int? = null

            val reachable = matrix.contains(nextPoint) && matrix[nextPoint] <= currentHeight + 1
            if (reachable) {
                cachedNextPointValue = minPathsCache[nextPoint]
            }

            if (cachedNextPointValue != null) {
                cachedNextPointValue + 1
            } else if (!reachable || visited[nextPoint]) {
                null
            } else {
                minPathRecursive(nextPoint, move.reverse)?.plus(1)
            }

        }.minByOrNull { it }

        if (pointCost != null) {
            println("Point cost: $point ($pointCost)")
            minPathsCache[point] = pointCost
        } else {
            println("PointNull: $point")
        }
        return pointCost
    }

}
