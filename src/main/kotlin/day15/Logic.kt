package day15

import shared.Point
import kotlin.math.abs

fun taxicabDistance(l: Point, r: Point): Int {
    return abs(l.x - r.x) + abs(l.y - r.y)
}