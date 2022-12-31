package day8

import day8.model.Tree
import utils.getDataLines
import utils.getDataScanner
import utils.printMatrix
import java.util.Scanner
import kotlin.math.max

fun main(args: Array<String>) {
    val input = getDataLines(8, args)
    val rows = input.size
    val columns = input[0].length

    val trees = Array(rows) { r ->
        Array(columns) { c ->
            Tree(input[r][c].digitToInt())
        }
    }



    println(countMaxScenicScore(trees))
}

fun countMaxScenicScore(trees: Array<Array<Tree>>): Int {
    val rows = trees.size
    val columns = trees[0].size

    var currentMax = -1

    for (r in 0 until rows) {
        for (c in 0 until columns) {
            currentMax = max(currentMax, countScenicScore(trees, r, c))
        }
    }

    return currentMax
}

fun countScenicScore(trees: Array<Array<Tree>>, row: Int, column: Int): Int {
    val height = trees[row][column].height

    var r = row
    var c = column

    fun countDirectionScore(updateStep: () -> Unit, checkBorders: () -> Boolean): Int {
        r = row
        c = column

        var insideBorders: Boolean
        var score = 0

        do {
            updateStep()
            insideBorders = checkBorders()
            if (insideBorders) {
                score++
            }
        } while (insideBorders && trees[r][c].height < height)

        return score

    }

    val topScore = countDirectionScore({ r-- }, { r >= 0 })
    if(topScore == 0) return 0

    val bottomScore = countDirectionScore({ r++ }, { r < trees.size })
    if(bottomScore == 0) return 0

    val leftScore = countDirectionScore({ c-- }, { c >= 0 })
    if(leftScore == 0) return 0

    val rightScore = countDirectionScore({ c++ }, { c < trees[0].size })
    if(rightScore == 0) return 0


    return topScore * bottomScore * leftScore * rightScore
}

fun countHiddenTrees(trees: Array<Array<Tree>>) {

    val rows = trees.size
    val columns = trees[0].size


    var visibleTreesCount = 0
    var currentMaxHeight = -1

    fun checkTree(tree: Tree) {
        if (tree.height > currentMaxHeight) {
            currentMaxHeight = tree.height
            if (!tree.visible) {
                tree.visible = true
                visibleTreesCount++
            }
        }
    }


    for (r in 0 until rows) {
        currentMaxHeight = -1
        for (c in 0 until columns) {
            checkTree(trees[r][c])
        }
        currentMaxHeight = -1
        for (c in columns - 1 downTo 0) {
            checkTree(trees[r][c])
        }
    }

    for (c in 0 until columns) {
        currentMaxHeight = -1
        for (r in 0 until rows) {
            checkTree(trees[r][c])
        }
        currentMaxHeight = -1
        for (r in rows - 1 downTo 0) {
            checkTree(trees[r][c])
        }
    }
}