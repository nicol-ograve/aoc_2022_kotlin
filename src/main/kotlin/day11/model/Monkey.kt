package day11.model

import day11.logic.Dispatcher
import day11.logic.MonkeyAction
import day11.logic.MonkeyOperation
import java.util.LinkedList
import java.util.Queue


class Monkey(
    val index: Int,
    initialItems: Array<Item>,
    val operation: MonkeyOperation,
    val action: MonkeyAction
) {
    val items: Queue<Item> = LinkedList()

    var inspections: Long = 0
        private set

    init {
        items.addAll(initialItems)
    }

    fun runTurn() {
        while (items.isNotEmpty()) {

            inspections++

            val item = items.poll()

            item.applyOperation(operation)

            val nextMonkeyIndex = action.destination(item)

            Dispatcher.dispatch(item, index, nextMonkeyIndex)

        }
    }

    fun receiveItem(item: Item) {
        items.offer(item)
    }

    override fun toString(): String {
        return "Monkey $index"
    }
}