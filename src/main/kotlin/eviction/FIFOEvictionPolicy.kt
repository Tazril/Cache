package eviction

import exceptions.EntriesExceededException
import java.util.LinkedList

class FIFOEvictionPolicy<Key>(override val maximumEntriesAllowed: Int) : EvictionPolicy<Key> {

    override fun getSize() = map.size

    private val linkedList = LinkedList<Key>()
    private val map = mutableMapOf<Key, Int>()

    init {
        require(0 < maximumEntriesAllowed) {
            "storage.Storage Size shall be greater than 0"
        }
    }

    override fun keyAccessed(key: Key) {
        map[key]?.let {
            // key exists already, don't do anything
            return
        } ?: run {
            // key does not exist, create space
            if (map.size == maximumEntriesAllowed) {
                throw EntriesExceededException()
            }
            // add entry
            linkedList.add(key)
            map[key] = linkedList.lastIndex
        }
    }

    override fun evictKey(): Key? {
        if(linkedList.isEmpty()) {
            return null
        }
        val key = linkedList.first
        linkedList.removeAt(0)
        map.remove(key)
        return key
    }


}