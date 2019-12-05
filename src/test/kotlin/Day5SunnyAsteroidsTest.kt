
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

    @ParameterizedTest
    @CsvSource(
        value = [
            "3,9,8,9,10,9,4,9,99,-1,8;3;0",
            "3,9,8,9,10,9,4,9,99,-1,8;8;1",
            "3,9,7,9,10,9,4,9,99,-1,8;7;1",
            "3,9,7,9,10,9,4,9,99,-1,8;8;0",
            "3,9,7,9,10,9,4,9,99,-1,8;9;0",
            "3,3,1108,-1,8,3,4,3,99;16;0",
            "3,3,1108,-1,8,3,4,3,99;8;1",
            "3,3,1107,-1,8,3,4,3,99;7;1",
            "3,3,1107,-1,8,3,4,3,99;8;0",
            "3,3,1107,-1,8,3,4,3,99;9;0",
            "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9;0;0",
            "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9;-42;1",
            "3,3,1105,-1,9,1101,0,0,12,4,12,99,1;0;0",
            "3,3,1105,-1,9,1101,0,0,12,4,12,99,1;666;1",
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99;-6;999",
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99;8;1000",
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99;10;1001"
        ], delimiter = ';'
    )
    fun programResultExamplesForMoreOpcodes(program: String, input: Int, expected: Int) {
        val programArray = program.split(",").stream().mapToInt { it.toInt() }.toArray()
        Assertions.assertEquals(expected, Day5SunnyAsteroids().programResult(programArray, input))
    }

    @Test
    fun programResultForMoreOpcodes() {
        val programArray = Files.lines(Paths.get("./src/test/resources/day5.txt"))
            .flatMap { it.split(",").stream() }
            .mapToInt { it.toInt() }
            .toArray()
        Assertions.assertEquals(16694270, Day5SunnyAsteroids().programResult(programArray, 5))
    }
}