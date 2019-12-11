import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day10MonitoringStationTest {

    @ParameterizedTest
    @CsvSource(
        ".#..#\\n" +
                ".....\\n" +
                "#####\\n" +
                "....#\\n" +
                "...##,8",
        "......#.#.\\n" +
                "#..#.#....\\n" +
                "..#######.\\n" +
                ".#.#.###..\\n" +
                ".#..#.....\\n" +
                "..#....#.#\\n" +
                "#..#....#.\\n" +
                ".##.#..###\\n" +
                "##...#..#.\\n" +
                ".#....####,33",
        " #.#....#.#\\n" + // Added empty space prefix because param string starting with # is considered "invalid" - but why?!?!?
                ".###....#.\\n" +
                ".#....#...\\n" +
                "##.#.#.#.#\\n" +
                "....#.#.#.\\n" +
                ".##..###.#\\n" +
                "..#...##..\\n" +
                "..##....##\\n" +
                "......#...\\n" +
                ".####.###.,35",
        ".#..#..###\\n" +
                "####.###.#\\n" +
                "....###.#.\\n" +
                "..###.##.#\\n" +
                "##.##.#.#.\\n" +
                "....###..#\\n" +
                "..#.#..#.#\\n" +
                "#..#.#.###\\n" +
                ".##...##.#\\n" +
                ".....#.#..,41",
        ".#..##.###...#######\\n" +
                "##.############..##.\\n" +
                ".#.######.########.#\\n" +
                ".###.#######.####.#.\\n" +
                "#####.##.#.##.###.##\\n" +
                "..#####..#.#########\\n" +
                "####################\\n" +
                "#.####....###.#.#.##\\n" +
                "##.#################\\n" +
                "#####.##.###..####..\\n" +
                "..######..##.#######\\n" +
                "####.##.####...##..#\\n" +
                ".#####..#.######.###\\n" +
                "##...#.##########...\\n" +
                "#.##########.#######\\n" +
                ".####.#.###.###.#.##\\n" +
                "....##.##.###..#####\\n" +
                ".#.#.###########.###\\n" +
                "#.#.#.#####.####.###\\n" +
                "###.##.####.##.#..##,210"
    )
    fun maxNumberOfOthersAsteroidsDetectedExamples(map: String, expected: Int) {
        assertEquals(expected, Day10MonitoringStation().maxNumberOfOthersAsteroidsDetected(map.trim().split("\\n")))
    }

    @Test
    fun maxNumberOfOthersAsteroidsDetected() {
        val map = Files.lines(Paths.get("./src/test/resources/day10.txt")).toList()
        assertEquals(0, Day10MonitoringStation().maxNumberOfOthersAsteroidsDetected(map))
    }

    @ParameterizedTest
    @CsvSource("2,4,2", "3,5,1", "12,6,6", "-1,-2,1", "-5,-10,5")
    fun greatestCommonDivisor(a: Int, b: Int, expected: Int) {
        assertEquals(expected, Day10MonitoringStation().greatestCommonDivisorPreservingSign(a, b))
    }
}