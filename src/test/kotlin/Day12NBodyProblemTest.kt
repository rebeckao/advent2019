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
        assertEquals(expected, Day12NBodyProblem().totalEnergy(moons.split("\\n"), steps))
        assertEquals(expected, Day12NBodyProblem().totalEnergyDecoupled(moons.split("\\n"), steps))
    }

    @Test
    fun totalEnergy() {
        val moons = Files.lines(Paths.get("./src/test/resources/day12.txt")).toList()
        assertEquals(8538, Day12NBodyProblem().totalEnergy(moons, 1000))
        assertEquals(8538, Day12NBodyProblem().totalEnergyDecoupled(moons, 1000))
    }

    @ParameterizedTest
    @CsvSource(
        value = ["<x=-1, y=0, z=2>\\n" +
                "<x=2, y=-10, z=-7>\\n" +
                "<x=4, y=-8, z=8>\\n" +
                "<x=3, y=5, z=-1>;2772"
            ,
            "<x=-8, y=-10, z=0>\\n" +
                    "<x=5, y=5, z=10>\\n" +
                    "<x=2, y=-7, z=3>\\n" +
                    "<x=9, y=-8, z=-3>;4686774924"
        ],
        delimiter = ';'
    )
    fun stepsToRepeatExamples(moons: String, expected: Long) {
        assertEquals(expected, Day12NBodyProblem().stepsToRepeatDecoupled(moons.split("\\n")))
    }

    @Test
    fun stepsToRepeat() {
        val moons = Files.lines(Paths.get("./src/test/resources/day12.txt")).toList()
        assertEquals(978349897231606, Day12NBodyProblem().stepsToRepeatDecoupled(moons))
    }
}