 package day9

import day9.model.rope.HeadTailRope
import day9.model.rope.MultiKnotsRope
import utils.getDataScanner

 fun main(args: Array<String>) {
    val scanner = getDataScanner(9, args)

    val reader = InputReader()

    // val rope = HeadTailRope()
     val rope = MultiKnotsRope(10)
     val positionsCounter = TailPositionsCounter(rope)

    while (scanner.hasNextLine()){
        val moves = reader.parseLine(scanner.nextLine())
        moves.forEach {
            rope.move(it)
        }
    }


    println(positionsCounter.visitedCellsCount)
}