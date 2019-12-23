
import Util.Companion.toLongArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class Day21SpringDroidTest {

    @Test
    fun amountOfHullDamageReportedByDroid() {
        val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day21.txt")))
        val instructions = listOf(
            "NOT A T",
            "NOT C J",
            "OR T J",
            "AND D J"
        )
        assertEquals(19357335L, Day21SpringDroid(program).amountOfHullDamageReportedByDroid(instructions))
    }
}