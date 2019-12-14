import Util.Companion.toLongArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class Day13CarePackageTest {

    @Test
    fun numberOfBlockTiles() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day13.txt")))
        assertEquals(213, Day13CarePackage(instructions).numberOfBlockTiles(program))
    }

    @Test
    fun initialGameState() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day13.txt")))
        program[0] = 2
        assertEquals(0, Day13CarePackage(instructions).playGame(program))
    }
}