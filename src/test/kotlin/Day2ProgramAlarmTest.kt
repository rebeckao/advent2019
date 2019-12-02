
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths

internal class Day2ProgramAlarmTest {

    @ParameterizedTest
    @CsvSource(value = ["1,9,10,3,2,3,11,0,99,30,40,50;3500",
    "1,0,0,0,99;2",
    "2,3,0,3,99;2",
    "2,4,4,5,99,0;2",
    "1,1,1,4,99,5,6,0,99;30"], delimiter = ';')
    fun programResultExamples(program: String, expected: Int) {
        val programArray = program.split(",").stream().mapToInt{it.toInt()}.toArray()
        Assertions.assertEquals(expected, Day2ProgramAlarm().programResult(programArray))
    }

    @Test
    fun programResult() {
        val programArray = Files.lines(Paths.get("./src/test/resources/day2.txt"))
            .flatMap { it.split(",").stream()}
            .mapToInt{it.toInt()}
            .toArray()
        programArray[1] = 12
        programArray[2] = 2
        Assertions.assertEquals(3101878, Day2ProgramAlarm().programResult(programArray))
    }

    @Test
    fun programParameters() {
        val programArray = Files.lines(Paths.get("./src/test/resources/day2.txt"))
            .flatMap { it.split(",").stream()}
            .mapToInt{it.toInt()}
            .toArray()
        Assertions.assertEquals(8444, Day2ProgramAlarm().paramsRequiredForProgramResult(19690720, programArray))
    }
}