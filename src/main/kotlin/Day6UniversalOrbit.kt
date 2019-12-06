class Day6UniversalOrbit {
    fun totalNumberOfOrbits(orbitStrings: List<String>): Int {
        val orbits = HashMap<String, HeavenlyBody>()
        for (orbit in orbitStrings) {
            val bodies = orbit.split(")")
            orbits[bodies[1]] = HeavenlyBody(bodies[0])
        }
        return orbits.values.stream()
            .mapToInt { it.totalOrbitCount(orbits) }
            .sum()
    }

    private class HeavenlyBody(private val orbitParent: String) {
        var orbitCount: Int? = null

        fun totalOrbitCount(orbits: HashMap<String, HeavenlyBody>): Int {
            if (orbitCount == null) {
                if (orbitParent == "COM") {
                    orbitCount = 1
                } else {
                    orbitCount = 1 + orbits[orbitParent]!!.totalOrbitCount(orbits)
                }
            }
            return orbitCount!!
        }
    }
}