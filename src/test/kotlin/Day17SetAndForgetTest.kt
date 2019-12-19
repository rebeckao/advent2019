
import Util.Companion.toLongArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class Day17SetAndForgetTest {

    @Test
    fun scaffoldIntersectionCalibrationExample() {
        val stringMap = "..#..........\n" +
                "..#..........\n" +
                "#######...###\n" +
                "#.#...#...#.#\n" +
                "#############\n" +
                "..#...#...#..\n" +
                "..#####...^.."
        assertEquals(76, Day17SetAndForget().scaffoldIntersectionCalibration(stringMap))
    }

    @Test
    fun scaffoldIntersectionCalibration() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day17.txt")))
        assertEquals(5948, Day17SetAndForget().scaffoldIntersectionCalibration(program))
    }

    @Test
    fun scaffoldNavigation() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day17.txt")))
        val mainMovementRoutine = "A,B,A,C,B,C,B,C,A,C"
        val programDefinitions = listOf(
            "R,12,L,6,R,12",
            "L,8,L,6,L,10",
            "R,12,L,10,L,6,R,10"
        )
        program[0] = 2
        val continuousVideoFeed=false
        assertEquals(997790L, Day17SetAndForget().navigateAsInstructed(program, mainMovementRoutine, programDefinitions, continuousVideoFeed))
    }
}