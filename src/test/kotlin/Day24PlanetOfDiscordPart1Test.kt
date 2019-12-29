import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day24PlanetOfDiscordPart1Test {

    @Test
    fun biodiversityOfFirstRepeatedStateExample() {
        val map = listOf(
            "....#",
            "#..#.",
            "#..##",
            "..#..",
            "#...."
        )
        assertEquals(2129920, Day24PlanetOfDiscordPart1(map).biodiversityOfFirstRepeatedState())
    }

    @Test
    fun biodiversityOfFirstRepeatedState() {
        val map = Files.lines(Paths.get("./src/test/resources/day24.txt")).toList()
        assertEquals(7543003, Day24PlanetOfDiscordPart1(map).biodiversityOfFirstRepeatedState())
    }
}