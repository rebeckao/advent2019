
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths

internal class Day5SunnyAsteroidsTest {

    @ParameterizedTest
    @CsvSource(
        value = [
            "3,0,4,0,99;42;42",
            "3,0,4,0,99;-1;-1",
            "1002,7,3,7,4,7,99,33;0;99",
            "1101,100,-1,7,4,7,99,42;0;99"
        ], delimiter = ';'
    )
    fun programResultExamples(program: String, input: Int, expected: Int) {
        val programArray = program.split(",").stream().mapToInt { it.toInt() }.toArray()
        Assertions.assertEquals(expected, Day5SunnyAsteroids().programResult(programArray, input))
    }

    @Test
    fun programResult() {
        val programArray = Files.lines(Paths.get("./src/test/resources/day5.txt"))
            .flatMap { it.split(",").stream() }
            .mapToInt { it.toInt() }
            .toArray()
        Assertions.assertEquals(16434972, Day5SunnyAsteroids().programResult(programArray, 1))
    }
}