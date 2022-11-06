import exceptions.KeyNotFoundException
import factory.CacheFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException


class CacheTest {

    lateinit var cache: Cache<String, String>
    val cacheFactory: CacheFactory<String, String> = CacheFactory()

    @Before
    fun init() {
        cache = cacheFactory.getDefault()
    }

    @Test
    fun shouldReturnValue_whenEntryIsAdded() {
        //given
        val key = "Key"
        val value = "Hello World"
        //when
        cache.put(key, value)
        //then
        assert(cache.get(key) == value)
    }

    @Test(expected = KeyNotFoundException::class)
    fun shouldFail_whenKeyIsMissing() {
        //given
        val key = "Key"
        //when
        //then
        cache.get(key)
    }

    @Test
    fun shouldReturnSizeEqualToMaximumEntries_whenCacheIsOverloaded() {
        //given
        val entries = Cache.MAXIMUM_ENTRIES + 5
        //when
        repeat(entries) {
            cache.put(it.toString(),it.toString())
            if(it < 5) cache.get("0")
        }
        //then
        println(cache.size().toString() + " " + Cache.MAXIMUM_ENTRIES.toString() )
        println(cache.getEntries())
        assert(cache.size() == Cache.MAXIMUM_ENTRIES)
    }
}