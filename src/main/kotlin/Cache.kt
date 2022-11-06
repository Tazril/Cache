import eviction.EvictionPolicy
import eviction.FIFOEvictionPolicy
import eviction.LRUEvictionPolicy
import exceptions.EntriesExceededException
import storage.HashMapStorage
import storage.Storage

class Cache<Key, Value>(
    private val evictionPolicy: EvictionPolicy<Key>,
    private val storage: Storage<Key, Value>
) {
    companion object {
        const val MAXIMUM_ENTRIES = 10
    }

    fun put(key: Key, value: Value) {
        try {
            evictionPolicy.keyAccessed(key)
            storage.put(key, value)
        } catch (e: EntriesExceededException) {
            println("Cache is full size = ${evictionPolicy.getSize()}. Performing Eviction")
            evictionPolicy.evictKey()?.let {
                storage.erase(it)
            }
            //retry
            put(key, value)
        }
    }

    fun get(key: Key): Value {
        val value = storage.get(key)
        evictionPolicy.keyAccessed(key)
        return value
    }

    fun size() = evictionPolicy.getSize()

    fun getEntries() = storage.getEntries()


    class Builder<Key, Value>() {

        private var evictionPolicy: EvictionPolicy<Key> = FIFOEvictionPolicy(MAXIMUM_ENTRIES)
        private var storage: Storage<Key, Value> = HashMapStorage()

        fun setEvictionPolicy(evictionPolicy: EvictionPolicy<Key>): Builder<Key, Value> {
            this.evictionPolicy = evictionPolicy
            return this
        }

        fun setStorage(storage: Storage<Key, Value>): Builder<Key, Value> {
            this.storage = storage
            return this
        }

        fun build() = Cache(evictionPolicy, storage)
    }


}