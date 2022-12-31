package day15

import shared.Point
import java.util.Scanner

val regex = Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)")

fun readSensorsInput(scanner: Scanner): List<Pair<Point, Point>> {

    val pairs = mutableListOf<Pair<Point, Point>>()

    while (scanner.hasNextLine()) {
        val matches = regex.find(scanner.nextLine())
        val (x1, y1, x2, y2) = matches!!.destructured
        pairs.add(
            Pair(Point(x1.toInt(), y1.toInt()), Point(x2.toInt(), y2.toInt()))
        )
    }

    return pairs
}