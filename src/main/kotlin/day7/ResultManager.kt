package day7

import day7.model.Directory

class ResultManager {
    companion object {
        var totalSize = 0


        var dirsMap = mutableMapOf<Directory, Int>()

        fun notifyDirectorySize(dir: Directory, size: Int) {
            dirsMap[dir] = size
            if(size < sizeThreshold){
                totalSize += size
            }
        }

        fun directoryToDeleteSize(requiredSpace: Int): Int {
            var deletionCandidateSize: Int = Int.MAX_VALUE
            dirsMap.forEach { dir, size ->
                if(size > requiredSpace && size < deletionCandidateSize){
                    deletionCandidateSize = size
                }
            }
            return deletionCandidateSize
        }
    }
}