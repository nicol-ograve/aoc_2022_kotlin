package day7

import day7.model.*

class Executor {
    private var currentDirectory = Directory("", null)
    private val rootDirectory = currentDirectory

    init {
        currentDirectory.addSubDir("/")
    }
    init {
        println()
    }

    fun calculateDirectoriesSize(): Int {
        return rootDirectory.calculateSize()
    }

    fun manageEvents(events: List<DirectoryEvent>) {
        events.forEach { manageEvent(it) }
    }

    fun manageEvent(event: DirectoryEvent) {
        when(event){
            is ListContent -> event.items.forEach{
                when(it){
                    is DirectoryContentFile -> currentDirectory.addFile(it.name, it.size)
                    is DirectoryContentSubDir -> currentDirectory.addSubDir(it.name)
                }
            }
            is NavigateTo -> currentDirectory = currentDirectory.getSubDir(event.directoryName)!!
            is NavigateToParent -> currentDirectory = currentDirectory.parent!!
        }
    }
}