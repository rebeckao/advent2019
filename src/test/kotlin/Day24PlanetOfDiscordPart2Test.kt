
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day24PlanetOfDiscordPart2Test {

    @Test
    fun bugsPresentInRecursiveGridAfterMinutesExample() {
        val map = listOf(
            "....#",
            "#..#.",
            "#..##",
            "..#..",
            "#...."
        )
        assertEquals(99, Day24PlanetOfDiscordPart2(map, 10).bugsPresentInRecursiveGridAfterMinutes())
    }

    @Test
    fun bugsPresentInRecursiveGridAfterMinutes() {
        val map = Files.lines(Paths.get("./src/test/resources/day24.txt")).toList()
        assertEquals(1975, Day24PlanetOfDiscordPart2(map, 200).bugsPresentInRecursiveGridAfterMinutes())
    }
}