import Day10MonitoringStation.Vector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.PI
import kotlin.math.atan
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
        assertEquals(282, Day10MonitoringStation().maxNumberOfOthersAsteroidsDetected(map))
    }

    @ParameterizedTest
    @CsvSource("2,4,2", "3,5,1", "12,6,6", "-1,-2,1", "-5,-10,5")
    fun greatestCommonDivisor(a: Long, b: Long, expected: Long) {
        assertEquals(expected, Util.greatestCommonDivisorPreservingSign(a, b))
    }

    @ParameterizedTest
    @CsvSource(
        "1,8,1",
        "9,15,1"
    )
    fun asteroidToBeVaporizedSmallExample(asteroidNumber: Int, expectedX: Int, expectedY: Int) {
        val map: List<String> = (".#....#####...#..\n" +
                "##...##.#####..##\n" +
                "##...#...#.#####.\n" +
                "..#.....#...###..\n" +
                "..#.#.....#....##").split("\n")
        assertEquals(
            Vector(x = expectedX, y = expectedY),
            Day10MonitoringStation().asteroidDestroyed(map, asteroidNumber)
        )
    }

    @ParameterizedTest
    @CsvSource(
        "1,11,12",
        "2,12,1",
        "3,12,2",
        "10,12,8",
        "20,16,0",
        "50,16,9",
        "100,10,16",
        "199,9,6",
        "200,8,2",
        "201,10,9",
        "299,11,1"
    )
    fun asteroidToBeVaporizedLargeExample(asteroidNumber: Int, expectedX: Int, expectedY: Int) {
        val map: List<String> = (".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##").split("\n")
        assertEquals(
            Vector(x = expectedX, y = expectedY),
            Day10MonitoringStation().asteroidDestroyed(map, asteroidNumber)
        )
    }

    @Test
    fun asteroidToBeVaporized() {
        val map = Files.lines(Paths.get("./src/test/resources/day10.txt")).toList()
        val asteroidDestroyed = Day10MonitoringStation().asteroidDestroyed(map, 200)
        assertEquals(1008, asteroidDestroyed.x * 100 + asteroidDestroyed.y)
    }

    @ParameterizedTest
    @CsvSource(
        "0,-1,0",
        "1,-1,0.25",
        "1,0,0.5",
        "1,1,0.75",
        "0,1,1",
        "-1,1,1.25",
        "-1,0,1.5",
        "-1,-1,1.75"
    )
    fun radians(x: Int, y: Int, expectedNumberOfPies: Double) {
        println(atan(0.0))
        assertEquals(expectedNumberOfPies * PI, Day10MonitoringStation().radians(Vector(x = x, y = y)))
    }
}
