package day6

import utils.getDataScanner

fun main(args: Array<String>) {
    val scanner = getDataScanner(6, args)

    val input = scanner.next()
    val result = findFirstMarker(input, 14);

    println(result)
}

fun findFirstMarker(input: String, size: Int): Int {
    val last4 = Array(size) { input[it] }
    var bufferOffset = size

    while (!last4.allDifferent()){
        last4[bufferOffset % size] = input[bufferOffset]
        bufferOffset++
    }


    return bufferOffset
}


fun Array<Char>.allDifferent(): Boolean {
    for (i in indices) {
        val value = get(i)
        for (j in i + 1 until size) {
            if (get(j) == value) {
                return false
            }
        }
    }
    return true
}