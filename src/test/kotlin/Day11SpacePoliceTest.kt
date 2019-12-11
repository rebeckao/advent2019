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

    @Test
    fun paintedPanels() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day11.txt")))
        val paintedPanels = Day11SpacePolice().paintedPanels(program, 1)
        val minX = paintedPanels.keys.map { it.x }.min()!!
        val minY = paintedPanels.keys.map { it.y }.min()!!
        val maxX = paintedPanels.keys.map { it.x }.max()!!
        val maxY = paintedPanels.keys.map { it.y }.max()!!
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                print(if ((paintedPanels.get(Day11SpacePolice.Position(x = x, y = y)) ?: 0) == 1) "#" else " ")
            }
            print("\n")
        }
    }
}