package day7.model

import day7.ResultManager

data class Directory(val name: String, val parent: Directory?) {
    private val subDirs = mutableListOf<Directory>()
    private val files = mutableListOf<File>()

    fun calculateSize(): Int {
        var size = 0

        files.forEach { size += it.size }
        subDirs.forEach {
            size += it.calculateSize()
        }

        ResultManager.notifyDirectorySize(this, size)
        return size
    }

    fun getSubDir(named: String): Directory? {
        return subDirs.firstOrNull { it.name == named }
    }

    fun addSubDir(name: String): Directory {
        return Directory(name, this).let {
            subDirs.add(it)
            it
        }
    }

    fun addFile(name: String, size: Int): File {
        return File(name, size).let {
            files.add(it)
            it
        }
    }
}