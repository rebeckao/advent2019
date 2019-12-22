import Util.Companion.toLongArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class Day19TractorBeamTest {

    @Test
    fun areasAffectedByTractorBeam() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day19.txt")))
        assertEquals(181, Day19TractorBeam(program).pointsAffectedByTractorBeam())
    }

    @Test
    fun closestSquareThatFitsInBeam() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day19.txt")))
        assertEquals(4240964, Day19TractorBeam(program).closestSquareThatFitsInBeam()) // too high: 4300977, 4260967
    }
}