package day5.model

import java.util.*

class CargoCrane(val stacks: Array<CargoStack>) {

    fun execute(commands: Stack<Command>) {
        for (command in commands){
            stacks[command.to - 1].push(
                stacks[command.from - 1].pop(command.count),
                true
            )
        }
    }
}