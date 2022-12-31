package day10.model

class Processor {

    var cycle = 0
        private set(value) {
            field = value
            onCycleCompleted?.invoke(
                value, x
            )
        }
    private var x = 1

    var onCycleCompleted: ((cycle: Int, value: Int) -> Unit)? = null


    fun execute(command: Command) {
        if(command is Nop){
            cycle++
        } else if(command is Add){
            cycle++
            cycle++
            x += command.x

        }
    }
}