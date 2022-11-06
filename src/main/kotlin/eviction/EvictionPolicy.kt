package eviction

interface EvictionPolicy <Key> {
    val maximumEntriesAllowed: Int
    fun keyAccessed(key: Key)
    fun evictKey() : Key?
    fun getSize(): Int
}