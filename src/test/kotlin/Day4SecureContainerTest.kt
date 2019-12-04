
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.*
import java.util.stream.IntStream
import kotlin.streams.toList

internal class Day4SecureContainerTest {

    @ParameterizedTest
    @CsvSource("111111, true", "223450, false", "123789, false")
    fun passwordsMatchingExamples(password: String, expected: Boolean) {
        assertEquals(if (expected) 1L else 0L, Day4SecureContainer().numberOfPasswordsMatching(Collections.singletonList(password)))
    }

    @Test
    fun passwordsMatching() {
        val passwords = IntStream.range(138307, 654504).mapToObj { it.toString() }.toList()
        assertEquals(1855, Day4SecureContainer().numberOfPasswordsMatching(passwords))
    }

    @ParameterizedTest
    @CsvSource("112233, true", "123444, false", "111122, true")
    fun passwordsMatchingWithOnlyTwoOfSomethingExamples(password: String, expected: Boolean) {
        assertEquals(if (expected) 1L else 0L, Day4SecureContainer().numberOfPasswordsMatchingWithOnlyTwoOfSomething(Collections.singletonList(password)))
    }

    @Test
    fun passwordsMatchingWithOnlyTwoOfSomething() {
        val passwords = IntStream.range(138307, 654504).mapToObj { it.toString() }.toList()
        assertEquals(1253, Day4SecureContainer().numberOfPasswordsMatchingWithOnlyTwoOfSomething(passwords))
    }
}