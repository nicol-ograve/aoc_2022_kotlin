package day18

import day18.model.Point3D

fun readInput(lines: List<String>): Array<Point3D> {
    return lines.map { it.split(",") }.map { Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt()) }.toTypedArray()
}