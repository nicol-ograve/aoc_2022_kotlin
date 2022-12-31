package day10

import day10.model.Command
import day10.model.Processor
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = getDataScanner(10, arrayOf(""))

    val result = calculateSum(scanner, arrayOf(20, 60, 100, 140, 180, 220))

}

fun calculateSum(scanner: Scanner, steps: Array<Int>): Int {

    var sum = 0
    var currentPixel = 0

    val processor = Processor()
    processor.onCycleCompleted = { cycle, value ->
        if (steps.contains(cycle)) {
            sum += value * cycle
        }

        if((cycle - 1) % 40 == 0){
            println()
            currentPixel = 0
        }


        if(currentPixel >= value - 1 && currentPixel <= value + 1){
            print("#")
        } else {
            print(".")
        }
        currentPixel++
    }

    while (scanner.hasNextLine()) {
        processor.execute(
            Command.from(scanner.nextLine())
        )
    }

    return sum
}

