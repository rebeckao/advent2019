
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day6UniversalOrbitTest {

    @Test
    fun totalNumberOfOrbitsExample() {
        val orbits = listOf(
            "COM)B",
            "B)C",
            "C)D",
            "D)E",
            "E)F",
            "B)G",
            "G)H",
            "D)I",
            "E)J",
            "J)K",
            "K)L"
        )
        assertEquals(42, Day6UniversalOrbit().totalNumberOfOrbits(orbits))
    }

    @Test
    fun totalNumberOfOrbits() {
        val orbits = Files.lines(Paths.get("./src/test/resources/day6.txt")).toList()
        assertEquals(333679, Day6UniversalOrbit().totalNumberOfOrbits(orbits))
    }

    @Test
    fun orbitTransferExample() {
        val orbits = listOf(
            "COM)B",
            "B)C",
            "C)D",
            "D)E",
            "E)F",
            "B)G",
            "G)H",
            "D)I",
            "E)J",
            "J)K",
            "K)L",
            "K)YOU",
            "I)SAN"
        )
        assertEquals(4, Day6UniversalOrbit().orbitTransfersBetweenYouAndSanta(orbits))
    }

    @Test
    fun orbitTransfers() {
        val orbits = Files.lines(Paths.get("./src/test/resources/day6.txt")).toList()
        assertEquals(370, Day6UniversalOrbit().orbitTransfersBetweenYouAndSanta(orbits))
    }
}