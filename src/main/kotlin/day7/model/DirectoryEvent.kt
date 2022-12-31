package day7.model


sealed interface DirectoryEvent
class NavigateTo(val directoryName: String) : DirectoryEvent
class NavigateToParent() : DirectoryEvent
class ListContent(val items: List<DirectoryContent>) : DirectoryEvent

sealed interface DirectoryContent
class DirectoryContentFile(val name: String, val size: Int) : DirectoryContent
class DirectoryContentSubDir(val name: String) : DirectoryContent

fun parseDirectoryEventsList(list: List<String>): List<DirectoryEvent> {
    var i = 0
    val events = mutableListOf<DirectoryEvent>()

    while (i < list.size) {
        val command = list[i]
        if (command.isCdToParentCommand()) {
            events.add(NavigateToParent())
        } else if (command.isCdCommand()) {
            events.add(NavigateTo(command.getCdCommandDestination()))
        } else if (command.isLsCommand()) {
            val contentList = mutableListOf<DirectoryContent>()
            while (i < list.size - 1 && !list[i + 1].isCommand()) {
                i++
                contentList.add(parseDirContentString(list[i]))
            }
            events.add(ListContent(contentList))
        } else {
            throw RuntimeException("Unknown command")
        }

        i++
    }


    return events
}

fun parseDirContentString(value: String): DirectoryContent {
    if (value.isSubdirectory()) {
        return DirectoryContentSubDir(value.getSubdirectoryName())
    } else {
        val parts = value.split(' ')
        return DirectoryContentFile(parts[1], parts[0].toInt())
    }
}

fun String.isCdToParentCommand(): Boolean {
    return this == "\$ cd .."
}

fun String.isCdCommand(): Boolean {
    return startsWith("\$ cd")
}

fun String.getCdCommandDestination(): String {
    return substring(5)
}

fun String.isLsCommand(): Boolean {
    return this == "\$ ls"
}

fun String.isCommand(): Boolean {
    return startsWith("\$ ")
}

fun String.isSubdirectory(): Boolean {
    return startsWith("dir ")
}

fun String.getSubdirectoryName(): String {
    return substring(4)
}