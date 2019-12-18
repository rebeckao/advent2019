import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day16FlawedFrequencyTest {

    @ParameterizedTest
    @CsvSource(
        "12345678,1,48226158",
        "12345678,2,34040438",
        "12345678,3,03415518",
        "12345678,4,01029498",
        "80871224585914546619083218645595,100,24176176",
        "19617804207202209144916044189917,100,73745418",
        "69317163492948606335995924319873,100,52432133"
    )
    fun firstEightDigitsInFinalOutputExamples(input: String, phases: Int, expected: String) {
        assertEquals(expected, Day16FlawedFrequency().firstEightDigitsInFinalOutput(input, phases))
    }

    @Test
    fun firstEightDigitsInFinalOutput() {
        val input = Files.lines(Paths.get("./src/test/resources/day16.txt")).toList()[0]
        assertEquals(74608727, Day16FlawedFrequency().firstEightDigitsInFinalOutput(input, 100))
    }
}