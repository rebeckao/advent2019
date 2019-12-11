
import Util.Companion.toLongArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.streams.toList

internal class Day9SensorBoostTest {

    @ParameterizedTest
    @CsvSource(
        value = ["109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99;109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99",
            "1102,34915192,34915192,7,4,7,99,0;1219070632396864",
            "104,1125899906842624,99;1125899906842624"], delimiter = ';'
    )
    fun resultOfIntComputerExamples(program: String, expected: String) {
        val programArray = toLongArray(program)
        val expectedList = expected.split(",").stream().mapToLong { it.toLong() }.toList()
        assertEquals(expectedList, Day9SensorBoost().resultOfIntComputer(programArray, ArrayDeque()))
    }

    @Test
    fun resultOfIntComputerTest() {
        val programArray = toLongArray(Files.lines(Paths.get("./src/test/resources/day9.txt")))
        val input = ArrayDeque<Long>()
        input.add(1)
        val actual = Day9SensorBoost().resultOfIntComputer(programArray, input)
        assertEquals(listOf(2870072642), actual)
    }


    @Test
    fun resultOfIntComputer() {
        val programArray = toLongArray(Files.lines(Paths.get("./src/test/resources/day9.txt")))
        val input = ArrayDeque<Long>()
        input.add(2)
        val actual = Day9SensorBoost().resultOfIntComputer(programArray, input)
        assertEquals(listOf(58534L), actual)
    }
}