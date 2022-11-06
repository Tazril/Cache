package eviction

import exceptions.EntriesExceededException
import java.util.LinkedList

class LRUEvictionPolicy<Key>(override val maximumEntriesAllowed: Int) : EvictionPolicy<Key> {

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
            // existing key entry is removed
            linkedList.removeAt(it)
        } ?: kotlin.run {
            // If size is full throw exception
            if (maximumEntriesAllowed == map.size) {
                throw EntriesExceededException()
            }
        }
        // add key to the end
        linkedList.add(key)
        map[key] = linkedList.lastIndex
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