package utils

import shared.Matrix


fun <T> printMatrix(matrix: Matrix<T>, printItem: ((item: T) -> String)? = null) {
    for (r in matrix.indices) {
        for (c in matrix[r].indices) {
            val item = matrix[r][c]
            print(printItem?.invoke(item) ?: item.toString())
        }
        println()
    }
}