import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day3CrossedWiresTest {

    @ParameterizedTest
    @CsvSource(
        value = ["R8,U5,L5,D3;U7,R6,D4,L4;6",
            "R75,D30,R83,U83,L12,D49,R71,U7,L72;U62,R66,U55,R34,D71,R55,D58,R83;159",
            "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51;U98,R91,D20,R16,D67,R40,U7,R15,U6,R7;135"], delimiter = ';'
    )
    fun manhattanDistanceToClosestIntersectionExamples(wire1: String, wire2: String, expected: Int) {
        Assertions.assertEquals(
            expected,
            Day3CrossedWires().manhattanDistanceToClosestIntersection(wire1.split(","), wire2.split(","))
        )
    }

    @Test
    fun manhattanDistanceToClosestIntersection() {
        val wires = Files.lines(Paths.get("./src/test/resources/day3.txt"))
            .map { it.split(",")}
            .toList()
        Assertions.assertEquals(855, Day3CrossedWires().manhattanDistanceToClosestIntersection(wires[0], wires[1]))
    }

}