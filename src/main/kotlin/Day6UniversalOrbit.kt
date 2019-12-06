import java.util.*
import kotlin.collections.HashMap

class Day6UniversalOrbit {
    fun totalNumberOfOrbits(orbitStrings: List<String>): Int {
        val orbits = mapOrbits(orbitStrings)
        return orbits.values.stream()
            .mapToInt { it.totalOrbitCount(orbits) }
            .sum()
    }

    fun orbitTransfersBetweenYouAndSanta(orbitStrings: List<String>): Int {
        val orbits = mapOrbits(orbitStrings)
        val yourOrbitParents = orbits["YOU"]!!.parents(orbits)
        val santasOrbitParents = orbits["SAN"]!!.parents(orbits)
        for (parent in yourOrbitParents) {
            if (santasOrbitParents.contains(parent)) {
                return yourOrbitParents.indexOf(parent) + santasOrbitParents.indexOf(parent)
            }
        }
        return 0
    }

    private fun mapOrbits(orbitStrings: List<String>): HashMap<String, HeavenlyBody> {
        val orbits = HashMap<String, HeavenlyBody>()
        for (orbit in orbitStrings) {
            val bodies = orbit.split(")")
            orbits[bodies[1]] = HeavenlyBody(bodies[0])
        }
        return orbits
    }

    private class HeavenlyBody(private val orbitParent: String) {
        var orbitCount: Int? = null
        var parents: LinkedList<String>? = null

        fun totalOrbitCount(orbits: HashMap<String, HeavenlyBody>): Int {
            if (orbitCount == null) {
                orbitCount = if (orbitParent == "COM") {
                    1
                } else {
                    1 + orbits[orbitParent]!!.totalOrbitCount(orbits)
                }
            }
            return orbitCount!!
        }

        fun parents(orbits: HashMap<String, HeavenlyBody>): LinkedList<String> {
            if (parents == null) {
                parents = LinkedList()
                parents!!.add(orbitParent)
                if (orbitParent != "COM") {
                    parents!!.addAll(orbits[orbitParent]!!.parents(orbits))
                }
            }
            return parents!!
        }
    }
}