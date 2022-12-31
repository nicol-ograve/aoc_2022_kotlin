package day5.model

import java.util.Stack

class CargoStack {
    val crates = Stack<Crate>()

    fun pop(count: Int = 1): Array<Crate> {
        return Array(count) { crates.pop() }
    }

    fun push(newCrates: Array<Crate>, keepOrder: Boolean = false){
        if(keepOrder){
            newCrates.reverse()
        }

        newCrates.forEach { crates.push(it) }
    }

    fun push(newCrate: Crate){
        crates.push(newCrate)
    }

    fun peek(): Crate? {
        return crates.peek()
    }

}