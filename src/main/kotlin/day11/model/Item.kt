package day11.model

import day11.logic.MonkeyOperation


class Item(initialWorryLevel: Long) {
    companion object {

        private var nextIndex = 0
    }

    val index: Int = nextIndex++


    var worryLevel: Long = initialWorryLevel


    fun applyOperation(operation:MonkeyOperation){
        worryLevel = operation.execute(worryLevel) / 3

    }

    override fun toString(): String {
        return "Item -> $worryLevel"
    }





}