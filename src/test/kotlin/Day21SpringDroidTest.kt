
import Util.Companion.toLongArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class Day21SpringDroidTest {

    @Test
    fun amountOfHullDamageReportedByDroidWalking() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day21.txt")))
        val instructions = listOf(
            "NOT A T",
            "NOT C J",
            "OR T J",
            "AND D J",
            "WALK"
        )
        assertEquals(19357335L, Day21SpringDroid(program).amountOfHullDamageReportedByDroid(instructions))
    }

    @Test
    fun amountOfHullDamageReportedByDroidRunning() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day21.txt")))
        val instructions = listOf(
            "OR A T",
            "AND B T",
            "AND C T",
            "NOT T T",
            "AND D T",
            "OR E J",
            "OR H J",
            "AND T J",
            "RUN"
        )
        assertEquals(1140147758L, Day21SpringDroid(program).amountOfHullDamageReportedByDroid(instructions))
    }
}