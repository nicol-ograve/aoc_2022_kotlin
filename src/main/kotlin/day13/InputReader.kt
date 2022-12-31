package day13

import day13.model.ListPacket
import day13.model.PacketsPair
import java.util.Scanner



fun readPacketsInput(scanner: Scanner): List<PacketsPair> {
    val list = mutableListOf<PacketsPair>()
    while (scanner.hasNextLine()){
        list.add(
            Pair(
                ListPacket(scanner.nextLine()),
                ListPacket(scanner.nextLine()),
            ))

        // Skip empty line between pairs
        if(scanner.hasNextLine()){
            scanner.nextLine()
        }
    }
    return  list
}