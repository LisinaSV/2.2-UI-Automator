package ru.netology.testing.uiautomator

// Импорты строго для JUnit 4
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalTest {

    @Test
    fun justTest() {
        // Теперь assertEquals будет виден, так как подключен junit:junit
        assertEquals(true, true)
    }
}
