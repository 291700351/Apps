package io.github.lee.core.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DataStorePreferencesTest {

    private val context = InstrumentationRegistry.getInstrumentation().context
    private val Context.ds by preferencesDataStore("hello")
    private val dsp = DataStorePreferences(context.ds)

    @Before
    fun setUp() {

    }

    @Test
    fun clear() {
    }

    @Test
    fun remove() {
    }

    @Test
    fun write() {
        runBlocking {
            dsp.write("name", "IceLee")
            val name = dsp.read("name".stringKey(), "")
            println("name = $name")
            Assert.assertEquals("IceLee", name)
        }
    }

    @Test
    fun testWrite() {
    }

    @Test
    fun read() {
    }
    @Test
    fun count(){
        runBlocking {
            Assert.assertEquals(1, dsp.count())
        }
    }
}