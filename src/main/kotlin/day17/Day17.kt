package day17

import day17.model.Pattern
import day17.model.TerrainLogic
import day17.services.GasIterator
import utils.getDataScanner

fun main(args: Array<String>) {
    val isDemo = false
    val scanner = getDataScanner(17, if (isDemo) arrayOf("demo") else emptyArray())
    var previousHeight = 0L

    val sequence = scanner.nextLine()
    var fallenRocks = 0L
    val increaseSteps = ArrayList<Long>()

    val terrain = TerrainLogic(
        GasIterator(sequence)
    ) {
        fallenRocks++

//        if (fallenRocks % 1 == 0L) {
            val height = it.height.toLong()
            increaseSteps.add(height - previousHeight)
            previousHeight = height
//        }
//        it.print()
//        println()
    }



    val goal = 1000000000000
//    while (fallenRocks < 1000000000000){
    while (fallenRocks < 5000) {
        terrain.nextMove()
//        terrain.print()
//        println()
    }

    val sortedPatterns = findPatterns(increaseSteps).sortedBy { it.length }.reversed()
//    val bestPattern = findPatterns(increaseSteps).maxBy { it.length }

    var foundValidPattern = false
    var currentPatternIndex = -1

    while (!foundValidPattern){

        currentPatternIndex++
        val currentPattern = sortedPatterns[currentPatternIndex]

        var i = currentPattern.second
        var invalid = false

        while (!invalid && i < increaseSteps.size){
            if(increaseSteps[i] != increaseSteps[currentPattern.first + ((i - currentPattern.first) % currentPattern.length)]) {
                invalid = true
            }
            i++
        }

        if( i == increaseSteps.size)
            foundValidPattern = true

    }

    val bestPattern = sortedPatterns[currentPatternIndex]
    val size = bestPattern.length
    println(size)

//    println(bestPattern)

    var sumBeforePattern = 0L
    for (i in 0 until bestPattern.first) {
        sumBeforePattern += increaseSteps[i]
    }


    var patternTotalSum = 0L
    for (i in bestPattern.first until bestPattern.second) {
        patternTotalSum += increaseSteps[i]
    }

    var index = 0L
    var totalHeight = 0L

    index = bestPattern.first.toLong()
    totalHeight += sumBeforePattern

    while (index < goal - bestPattern.length) {
        totalHeight += patternTotalSum
        index += bestPattern.length
    }

    val missingSteps = goal - index
    for (i in 0 until missingSteps){
        totalHeight += increaseSteps[(bestPattern.first.toLong() + i).toInt()]
    }

//        terrain.print()
//        println()
    println(totalHeight)
    println(1514285714288)

    println(totalHeight - 1514285714288L)
    // Too high: 94357142846015
    // Too high: 1832528533388
    // Too low:  1594842406877


}


/********************* PATTERNS ****************************/

fun findPatterns(list: ArrayList<Long>): Array<Pattern> {
    val patterns = mutableListOf<Pattern>()

    for (i in list.indices) {
        for (j in i + 1 until list.size) {
            if (list[i] == (list[j])) {
                patterns.add(findPattern(list, i, j))
            }
        }
    }
    return patterns.toTypedArray()

}

fun findPattern(list: ArrayList<Long>, first: Int, second: Int): Pattern {
    var offset = 1
    while (first + offset < second && second + offset < list.size && list[first + offset] == (list[second + offset])) {
        offset++
    }
    return Pattern(first, second, offset)
}


