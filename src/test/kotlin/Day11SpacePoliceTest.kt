import Util.Companion.toLongArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class Day11SpacePoliceTest {

    @Test
    fun numberOfPanelsPainted() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day11.txt")))
        assertEquals(2339, Day11SpacePolice().numberOfPanelsPainted(program))
    }
}