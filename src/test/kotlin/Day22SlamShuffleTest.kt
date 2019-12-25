import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day22SlamShuffleTest {

    @ParameterizedTest
    @CsvSource(
        "cut 3,1,8",
        "cut -6,2,8",
        "deal into new stack,2,7",
        "deal with increment 7\\n" +
                "deal into new stack\\n" +
                "deal into new stack,3,1",
        "cut 6\\n" +
                "deal with increment 7\\n" +
                "deal into new stack,4,3",
        "deal with increment 7\\n" +
                "deal with increment 9\\n" +
                "cut -2,5,7",
        "deal into new stack\\n" +
                "cut -2\\n" +
                "deal with increment 7\\n" +
                "cut 8\\n" +
                "cut -4\\n" +
                "deal with increment 7\\n" +
                "cut 3\\n" +
                "deal with increment 9\\n" +
                "deal with increment 3\\n" +
                "cut -1,6,9",
        "deal with increment 3,7,1",
        "deal with increment 3,8,4"

    )
    fun cardIndexAfterShuffles(shuffles: String, startIndex: Int, expected: Int) {
        assertEquals(expected, Day22SlamShuffle().cardIndexAfterShuffles(startIndex, 10, shuffles.split("\\n")))
    }

    @Test
    fun cardIndexAfterShuffles() {
        val shuffles = Files.lines(Paths.get("./src/test/resources/day22.txt")).toList()
        assertEquals(8326, Day22SlamShuffle().cardIndexAfterShuffles(2019, 10_007, shuffles))
    }

    @ParameterizedTest
    @CsvSource(
        "cut 3,1,4",
        "cut -6,2,6",
        "deal into new stack,2,7",
        "deal with increment 7\\n" +
                "deal into new stack\\n" +
                "deal into new stack,3,9",
        "cut 6\\n" +
                "deal with increment 7\\n" +
                "deal into new stack,4,1",
        "deal with increment 7\\n" +
                "deal with increment 9\\n" +
                "cut -2,5,1",
        "deal into new stack\\n" +
                "cut -2\\n" +
                "deal with increment 7\\n" +
                "cut 8\\n" +
                "cut -4\\n" +
                "deal with increment 7\\n" +
                "cut 3\\n" +
                "deal with increment 9\\n" +
                "deal with increment 3\\n" +
                "cut -1,6,7",
        "deal with increment 3,0,0",
        "deal with increment 3,1,7",
        "deal with increment 3,2,4",
        "deal with increment 3,3,1",
        "deal with increment 3,4,8",
        "deal with increment 3,5,5",
        "deal with increment 3,6,2",
        "deal with increment 3,7,9",
        "deal with increment 3,8,6",
        "deal with increment 3,9,3",
        "deal with increment 7,0,0",
        "deal with increment 7,1,3",
        "deal with increment 7,2,6",
        "deal with increment 7,3,9",
        "deal with increment 7,4,2",
        "deal with increment 7,5,5",
        "deal with increment 7,6,8",
        "deal with increment 7,7,1",
        "deal with increment 7,8,4",
        "deal with increment 7,9,7"

    )
    fun cardIndexAfterReverseShufflesExamples(shuffles: String, endIndex: Long, expected: Long) {
        assertEquals(
            expected,
            Day22SlamShuffle().cardIndexBeforeShuffles(endIndex, 10, 1, shuffles.split("\\n"))
        )
    }

    @Test
    fun cardIndexAfterShufflesInHugeDeck() {
        val shuffles = Files.lines(Paths.get("./src/test/resources/day22.txt")).toList()
        assertEquals(0, Day22SlamShuffle().cardIndexBeforeShuffles(2020, 119315717514047, 101741582076661, shuffles))
    }
}