import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day12NBodyProblemTest {

    @ParameterizedTest
    @CsvSource(
        value = ["<x=-1, y=0, z=2>\\n" +
                "<x=2, y=-10, z=-7>\\n" +
                "<x=4, y=-8, z=8>\\n" +
                "<x=3, y=5, z=-1>;10;179",
            "<x=-8, y=-10, z=0>\\n" +
                    "<x=5, y=5, z=10>\\n" +
                    "<x=2, y=-7, z=3>\\n" +
                    "<x=9, y=-8, z=-3>;100;1940"],
        delimiter = ';'
    )
    fun totalEnergyExamples(moons: String, steps: Int, expected: Int) {
        assertEquals(expected, Day12NBodyProblem().totalEnergyDecoupled(moons.split("\\n"), steps))
    }

    @Test
    fun totalEnergy() {
        val moons = Files.lines(Paths.get("./src/test/resources/day12.txt")).toList()
        assertEquals(8538, Day12NBodyProblem().totalEnergyDecoupled(moons, 1000))
    }

    @ParameterizedTest
    @CsvSource(
        value = ["<x=-1, y=0, z=2>\\n" +
                "<x=2, y=-10, z=-7>\\n" +
                "<x=4, y=-8, z=8>\\n" +
                "<x=3, y=5, z=-1>;2772",
            "<x=-8, y=-10, z=0>\\n" +
                    "<x=5, y=5, z=10>\\n" +
                    "<x=2, y=-7, z=3>\\n" +
                    "<x=9, y=-8, z=-3>;4686774924",
            "<x=-4, y=-9, z=-3>\\n" +
                    "<x=-13, y=-11, z=0>\\n" +
                    "<x=-17, y=-7, z=15>\\n" +
                    "<x=-16, y=4, z=2>;548525804273976"
        ],
        delimiter = ';'
    )
    fun stepsToRepeatExamples(moons: String, expected: Long) {
        assertEquals(expected, Day12NBodyProblem().stepsToRepeatDecoupled(moons.split("\\n")))
    }

    @ParameterizedTest
    @CsvSource(
        "-1,2,4,3,18",
        "0,-10,-8,5,28",
        "2,-7,8,-1,44",
        "-8,5,2,9,2028",
        "-10,5,-7,-8,5898",
        "0,10,3,-3,4702",
        "-4,-13,-17,-16,113028",
        "-9,-11,-7,4,167624",
        "-3,0,15,2,231614"
    )
    fun stepsToRepeatForDimension(pos0: Int, pos1: Int, pos2: Int, pos3: Int, expected: Long) {
        assertEquals(expected, Day12NBodyProblem().stepsToRepeatForDimension(intArrayOf(pos0, pos1, pos2, pos3)))
    }

    @Test
    fun stepsToRepeat() {
        val moons = Files.lines(Paths.get("./src/test/resources/day12.txt")).toList()
        assertEquals(506359021038056, Day12NBodyProblem().stepsToRepeatDecoupled(moons))
    }
}