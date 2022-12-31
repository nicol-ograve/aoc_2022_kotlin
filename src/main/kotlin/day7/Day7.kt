 package day7

import day7.model.parseDirectoryEventsList
import utils.getDataLines

 const val sizeThreshold = 100000
 const val totalSpace = 70000000
 const val requiredSpace = 30000000

fun main(args: Array<String>) {
    val data = getDataLines(7, args)

    val events = parseDirectoryEventsList(data)

    val executor = Executor()
    executor.manageEvents(events)
    val usedSpace = executor.calculateDirectoriesSize()

    var unusedSpace = totalSpace - usedSpace
    val spaceToFree = requiredSpace - unusedSpace

    
    val result = ResultManager.directoryToDeleteSize(spaceToFree)
    
    println(result)
}



 fun getDirectoriesSizeSum(commands: List<String>): Int {
     return 1;
 }