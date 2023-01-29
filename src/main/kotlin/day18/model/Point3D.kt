package day18.model

import kotlin.math.abs

data class Point3D(val x: Int, val y: Int, val z: Int) {

    constructor(coordinates: Array<Int>) : this(coordinates[0], coordinates[1], coordinates[2])

    val coordinates = arrayOf(x, y, z)


    fun neighborOf(p: Point3D): Boolean {
        val coordsSum = coordinates.mapIndexed { index, item -> abs(item - p.coordinates[index]) }.sorted()
        return coordsSum[0] == 0 && coordsSum[1] == 0 && coordsSum[2] == 1
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    fun nearPoints(): Array<Point3D> {
        return arrayOf(
            this.copy(x = x - 1),
            this.copy(x = x + 1),
            this.copy(y = y - 1),
            this.copy(y = y + 1),
            this.copy(z = z - 1),
            this.copy(z = z + 1),
        )
    }
}