package factory

import Cache
import eviction.FIFOEvictionPolicy
import storage.TreeMapStorage

class CacheFactory<Key, Value> {

    fun getDefault(): Cache<Key, Value> {
        return Cache.Builder<Key, Value>().build()
    }

    fun getInstance(cacheType: CacheType): Cache<Key, Value> {
        return when (cacheType) {
            CacheType.LRU_CACHE -> Cache.Builder<Key, Value>().build()
            CacheType.LRU_CACHE_TIME_OPTIMISED -> Cache.Builder<Key, Value>().setStorage(TreeMapStorage()).build()
            CacheType.FIFO_CACHE -> Cache.Builder<Key, Value>()
                .setEvictionPolicy(FIFOEvictionPolicy(Cache.MAXIMUM_ENTRIES)).build()
        }
    }
}

