
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
        val map = HashMap<Pair<Int, Int>, Char>().toMutableMap()
        val listMap = stringMap.split("\n")
        for (y in listMap.indices) {
            for (x in listMap[y].indices) {
                map[Pair(x, y)] = listMap[y][x]
            }
        }
        assertEquals(76, Day17SetAndForget().scaffoldIntersectionCalibration(map))
    }

    @Test
    fun scaffoldIntersectionCalibration() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day17.txt")))
        assertEquals(5948, Day17SetAndForget().scaffoldIntersectionCalibration(program))
    }
}