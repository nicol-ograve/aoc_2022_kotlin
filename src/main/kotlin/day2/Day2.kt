package day2

import java.io.File
import java.lang.RuntimeException
import java.util.*


fun indexForOtherPlayerSymbol(symbol: String) = when (symbol) {
    "A" -> 0
    "B" -> 1
    "C" -> 2
    else -> throw RuntimeException("Invalid other player' symbol")
}

fun indexForMySymbol(symbol: String) = when (symbol) {
    "X" -> 0
    "Y" -> 1
    "Z" -> 2
    else -> throw RuntimeException("Invalid my symbol")
}

private val pointsMatrix = arrayOf(
    // Me = r    , p     , s
    arrayOf(1 + 3, 2 + 6, 3 + 0), // other = rock
    arrayOf(1 + 0, 2 + 3, 3 + 6), // other = paper
    arrayOf(1 + 6, 2 + 0, 3 + 3)  // other = scissor
)

fun rps_points(filePath: String): Int {

    val scanner = Scanner( File(filePath))
    var total = 0

    while(scanner.hasNextLine()){
        val otherPlayerIndex = indexForOtherPlayerSymbol(scanner.next())
        val myIndex = indexForMySymbol(scanner.next())
        total += pointsMatrix[otherPlayerIndex][myIndex]
    }

    return total

}
fun rps_points_advanced(filePath: String): Int {

    val scanner = Scanner( File(filePath))
    var total = 0

    while(scanner.hasNextLine()){
        val otherPlayerIndex = indexForOtherPlayerSymbol(scanner.next())
        val myIndex = indexForMySymbolAdvanced(otherPlayerIndex,scanner.next())
        total += pointsMatrix[otherPlayerIndex][myIndex]
    }

    return total

}

fun indexForMySymbolAdvanced(otherPlayerIndex: Int, mySymbol: String): Int = when(mySymbol){
    "X" -> (otherPlayerIndex + 2) % 3
    "Y" -> otherPlayerIndex
    "Z" -> (otherPlayerIndex + 1) % 3
    else -> throw RuntimeException("Invalid my symbol")
}