package day3

import day2.rps_points_advanced
import utils.getDataScanner
import java.util.Scanner


fun main(args: Array<String>) {
    val scanner = getDataScanner(3, args)

    val result = findGroupsPrioritiesSum(scanner)

    println(result)
}

//Part 1
fun findPrioritiesSum(scanner: Scanner): Int {
    var sum = 0

    while (scanner.hasNextLine()) {
        val rucksack = Rucksack(scanner.nextLine())
        val wrongPositionedItem = getWrongPositionedItem(rucksack)
        sum += getItemValue(wrongPositionedItem)
    }

    return sum
}


fun getWrongPositionedItem(rucksack: Rucksack): Char {
    val set = HashSet<Char>()
    rucksack.firstCompartmentItems.forEach { set.add(it) }
    return rucksack.secondCompartmentItems.first { set.contains(it) }
}


// Part 2

fun findGroupsPrioritiesSum(scanner: Scanner): Int {
    var sum = 0

    while (scanner.hasNextLine()) {
        val set = HashSet<Char>()
        val rucksacks = Array(3) { Rucksack(scanner.nextLine()) }
        val wrongPositionedItem = getGroupsCommonItem(rucksacks)
        sum += getItemValue(wrongPositionedItem)
    }

    return sum
}

fun getGroupsCommonItem(rucksacks: Array<Rucksack>): Char {
    val sets = rucksacks.map {
        val set = HashSet<Char>()
        it.content.forEach {item -> set.add(item) }
        set
    }
    val result = sets[0]

    for(i in 1 until sets.size) {
        result.removeIf { !sets[i].contains(it) }
    }
    if(result.size != 1){
        throw RuntimeException("No badge found")
    }
    return result.first()
}

// Utils

fun getItemValue(item: Char): Int {
    return if (item.isUpperCase())
        item.code - 'A'.code + 27
    else
        item.code - 'a'.code + 1
}


