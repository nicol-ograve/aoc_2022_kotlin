package day10.model

sealed class Command {
    companion object {
        fun from(command: String): Command{
            if(command == "noop"){
                return Nop
            } else {
               return Add(
                   command.split(" ")[1].toInt()
               )
            }
        }
    }
}


class Add(val x: Int) : Command() {}
object Nop : Command() {}