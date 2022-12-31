package day1

import java.io.File
import java.util.Scanner

fun mostCaloriesElf(filePath: String): Int {

    val scanner = Scanner( File(filePath))

    var max = -1
    var current = 0
    while(scanner.hasNext()){
        val line = scanner.nextLine()
        if(line.isEmpty()) {
            max = Math.max(current, max)
            current = 0
        } else {
            current += line.toInt()
        }
    }
    max = Math.max(current, max)

    return max
}
fun top3CaloriesElves(filePath: String): Int {
    val scanner = Scanner( File(filePath))
    val top3 = Array(3){-1}

    var current = 0

    val updateTop3 = {
        if(current > top3[0]) {
            top3[2] = top3[1]
            top3[1] = top3[0]
            top3[0] = current
        } else if(current > top3[1]) {
            top3[2] = top3[1]
            top3[1] = current
        } else if(current > top3[2]) {
            top3[2] = current
        }
    };

    while(scanner.hasNext()){
        val line = scanner.nextLine()
        if(line.isEmpty()){
            updateTop3()
            current = 0
        } else {
            current += line.toInt()
        }
    }
    updateTop3()

    return top3.sum()

}