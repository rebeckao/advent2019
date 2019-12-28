import Util.Companion.toLongArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class Day23CategorySixTest {

    @Test
    fun firstPacketSentTo255() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day23.txt")))
        assertEquals(20225L, Day23CategorySix(program).firstPacketSentTo255())
    }

    @Test
    fun firstPacketSentTwiceByNat() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day23.txt")))
        assertEquals(14348L, Day23CategorySix(program).firstPacketSentTwiceByNat())
    }
}