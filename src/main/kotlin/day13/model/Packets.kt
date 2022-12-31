package day13.model

sealed interface Packet {}

class IntPacket(val value: Int) : Packet {}

class ListPacket(val value: String) : Packet {

    override fun toString(): String {
        return value
    }
    private val packetString = if(value.isEmpty() || value.length == 1) value else value.substring(1, value.length - 1)

    constructor(value: Int) : this("[$value]")

    fun iterator(): Iterator<Packet> {
        var string = packetString

        fun isNextPacketInteger(): Boolean {
            return string[0] != '['
        }

        fun nextIntegerPacket(): IntPacket {
            if (!string.contains(",") && !string.contains("]")) {
                val nextInt = string.toInt()
                string = ""
                return IntPacket(nextInt)
            } else {
                var endIndex = string.indexOf(',')
                if (endIndex == -1) {
                    endIndex = string.indexOf(']')
                }
                val nextInt = string.substring(0, endIndex)
                string = string.substring(endIndex + 1)
                return IntPacket(nextInt.toInt())
            }
        }

        fun nextListPacket(): ListPacket {
            var parenthesisCount = 1
            var packetEndIndex = 0

            while (parenthesisCount > 0) {
                packetEndIndex++
                when (string[packetEndIndex]) {
                    '[' -> parenthesisCount++
                    ']' -> parenthesisCount--
                }
            }

            val nextPacket = string.substring(0, packetEndIndex + 1)
            string = string.substring(packetEndIndex)
            return ListPacket(nextPacket)

        }

        return object : Iterator<Packet> {
            override fun hasNext(): Boolean = string.isNotEmpty()

            override fun next(): Packet {
                val nextPacket = if (isNextPacketInteger()) nextIntegerPacket() else nextListPacket()

                while (string.isNotEmpty() && (string[0] == ',' || string[0] == ']')) {
                    string = string.substring(1)
                }

                return nextPacket
            }
        }
    }
}

typealias PacketsPair = Pair<ListPacket, ListPacket>
