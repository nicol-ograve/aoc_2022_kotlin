package shared

typealias Matrix<T> = Array<Array<T>>

operator fun <T> Array<Array<T>>.get(point: Point): T {
    return this[point.y][point.x]
}

operator fun <T> Array<Array<T>>.set(point: Point, value: T) {
    this[point.y][point.x] = value
}

fun Array<Array<Int>>.contains(point: Point): Boolean {
    return point.x >= 0 && point.y >= 0 && point.y < this.size && point.x < this[0].size
}


