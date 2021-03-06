
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

internal class Day18ManyWorldsTest {

    @ParameterizedTest
    @CsvSource(
        "ö#########\\n" +
                "#b.A.@.a#\\n" +
                "#########,8",
        "ö########################\\n" +
                "#f.D.E.e.C.b.A.@.a.B.c.#\\n" +
                "######################.#\\n" +
                "#d.....................#\\n" +
                "########################,86",
        "ö########################\\n" +
                "#...............b.C.D.f#\\n" +
                "#.######################\\n" +
                "#.....@.a.B.c.d.A.e.F.g#\\n" +
                "########################,132",
        "ö#################\\n" +
                "#i.G..c...e..H.p#\\n" +
                "########.########\\n" +
                "#j.A..b...f..D.o#\\n" +
                "########@########\\n" +
                "#k.E..a...g..B.n#\\n" +
                "########.########\\n" +
                "#l.F..d...h..C.m#\\n" +
                "#################,136",
        "ö########################\\n" +
                "#@..............ac.GI.b#\\n" +
                "###d#e#f################\\n" +
                "###A#B#C################\\n" +
                "###g#h#i################\\n" +
                "########################,81"
    )
    fun leastStepsToCollectAllKeysExamples(rawMap: String, expected: Int) {
        val map = rawMap.replace("ö", "").split("\\n")
        assertEquals(expected, Day18ManyWorlds(map).shortestPathToCollectAllKeys())
    }

    @Test
    fun leastStepsToCollectAllKeys() {
        val map = Files.lines(Paths.get("./src/test/resources/day18.txt")).toList()
        assertEquals(3646, Day18ManyWorlds(map).shortestPathToCollectAllKeys())
    }

    @ParameterizedTest
    @CsvSource(
        "ö#######\\n" +
                "#a.#Cd#\\n" +
                "##@#@##\\n" +
                "#######\\n" +
                "##@#@##\\n" +
                "#cB#Ab#\\n" +
                "#######,8",
        "ö###############\\n" +
                "#d.ABC.#.....a#\\n" +
                "######@#@######\\n" +
                "###############\\n" +
                "######@#@######\\n" +
                "#b.....#.....c#\\n" +
                "###############,24",
        "ö#############\\n" +
                "#DcBa.#.GhKl#\\n" +
                "#.###@#@#I###\\n" +
                "#e#d#####j#k#\\n" +
                "###C#@#@###J#\\n" +
                "#fEbA.#.FgHi#\\n" +
                "#############,32",
        "ö#############\\n" +
                "#g#f.D#..h#l#\\n" +
                "#F###e#E###.#\\n" +
                "#dCba@#@BcIJ#\\n" +
                "#############\\n" +
                "#nK.L@#@G...#\\n" +
                "#M###N#H###.#\\n" +
                "#o#m..#i#jk.#\\n" +
                "#############,72"
    )
    fun collectKeysInMultipleDungeonsExamples(rawMap: String, expected: Int) {
        val map = rawMap.replace("ö", "").split("\\n")
        assertEquals(expected, Day18ManyWorlds(map).shortestPathToCollectAllKeys())
    }

    @Test
    fun collectKeysInMultipleDungeons() {
        val map = Files.lines(Paths.get("./src/test/resources/day18_2.txt")).toList()
        assertEquals(1730, Day18ManyWorlds(map).shortestPathToCollectAllKeys())
    }

}