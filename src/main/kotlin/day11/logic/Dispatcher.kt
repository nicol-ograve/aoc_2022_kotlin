package day11.logic

import day11.model.Item
import day11.model.Monkey

var mcm = 1000L

object Dispatcher {
    val itemsList = HashMap<Item, ArrayList<DispatchEvent>>()
    var monkeys: Array<Monkey>? = null
        set(value) {
            field = value
            mcm = value?.map { it.action.divisibleBy }?.reduce { acc, l -> acc * l } ?: 100L
            monkeys?.forEach { monkey ->
                monkey.items.forEach {
                    itemsList[it] = arrayListOf(DispatchEvent(it.index, it.worryLevel, monkey.index, null))
                }
            }
        }


    fun dispatch(item: Item, fromMonkeyIndex: Int, monkeyIndex: Int) {
        itemsList[item]?.add(DispatchEvent(item.index, item.worryLevel, monkeyIndex, fromMonkeyIndex))
        if (item.worryLevel > mcm)
            item.worryLevel = ( item.worryLevel % mcm)+mcm

        monkeys?.get(monkeyIndex)?.receiveItem(item)
    }
}


data class DispatchEvent(val itemIndex: Int, val worryLevel: Long, val toMonkey: Int, val fromMonkey: Int?) {

    override fun toString(): String {
        return "$itemIndex ($worryLevel): from ${fromMonkey ?: '-'} to $toMonkey"
    }
}