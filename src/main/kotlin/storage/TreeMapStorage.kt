package storage

import exceptions.KeyNotFoundException

class TreeMapStorage<Key, Value> : Storage<Key, Value> {

    private val storageMap = mutableMapOf<Key, Value>()
    override fun put(key: Key, value: Value) {
        storageMap[key] = value
    }

    override fun erase(key: Key): Key {
        storageMap.remove(key)
        return key
    }

    override fun get(key: Key): Value {
        return storageMap[key]?: throw KeyNotFoundException()
    }

    override fun getEntries(): Map<Key, Value> {
        return storageMap
    }
}