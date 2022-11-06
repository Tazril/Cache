package storage

interface Storage<Key, Value> {

    fun put(key: Key, value: Value)

    fun erase(key: Key) : Key?

    fun get(key: Key) : Value

    fun getEntries(): Map<Key, Value>
}