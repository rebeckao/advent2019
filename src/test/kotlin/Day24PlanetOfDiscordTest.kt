import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day24PlanetOfDiscordTest {

    @Test
    fun biodiversityOfFirstRepeatedStateExample() {
        val map = listOf(
            "....#",
            "#..#.",
            "#..##",
            "..#..",
            "#...."
        )
        assertEquals(2129920, Day24PlanetOfDiscord(map).biodiversityOfFirstRepeatedState())
    }

    @Test
    fun biodiversityOfFirstRepeatedState() {
        val map = Files.lines(Paths.get("./src/test/resources/day24.txt")).toList()
        assertEquals(7543003, Day24PlanetOfDiscord(map).biodiversityOfFirstRepeatedState())
    }
}