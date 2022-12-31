package day11.logic

import day11.model.Item


class MonkeyAction(val divisibleBy: Long, private val destinationIndexIfTrue: Int, private val destinationIndexIfFalse: Int) {

    fun destination(item: Item): Int{
        val divisible = item.worryLevel % divisibleBy == 0L
        return if(divisible) destinationIndexIfTrue else destinationIndexIfFalse
    }
}
