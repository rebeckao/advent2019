
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day8SpaceImageFormatTest {

    @Test
    fun corruptionCheck() {
        val layerLength = 25*6
        val layerWithFewestZeros = Files.lines(Paths.get("./src/test/resources/day8.txt")).toList()[0]
            .chunked(layerLength)
            .map{ it -> it.groupBy{it}}
            .minBy{it.getOrDefault('0', emptyList()).size}
            .orEmpty()
        val numberOfOneDigits = layerWithFewestZeros.getOrDefault('1', emptyList()).size
        val numberOfTwoDigits = layerWithFewestZeros.getOrDefault('2', emptyList()).size
        assertEquals(1742, numberOfOneDigits*numberOfTwoDigits)
    }

    @Test
    fun decodedImageExample() {
        assertEquals("01\n10", Day8SpaceImageFormat().decodedImage("0222112222120000", 2, 2))
    }

    @Test
    fun decodedImage() {
        val rawLayers = Files.lines(Paths.get("./src/test/resources/day8.txt")).toList()[0]
        val decodedImage = Day8SpaceImageFormat().decodedImage(rawLayers, 25, 6)
        println("decodedImage = \n${decodedImage.replace('1', '*').replace("0", " ")}")
    }
}