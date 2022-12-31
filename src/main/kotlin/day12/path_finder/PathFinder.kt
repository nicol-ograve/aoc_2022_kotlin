package day12.path_finder

import shared.Point

interface PathFinder {
    fun findMinPathLength(startPoint: Point): Int?;
    fun findMinPathLength(startingPoints: Array<Point>): Int?;
}

