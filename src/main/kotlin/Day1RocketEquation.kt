import java.lang.Integer.max

class Day1RocketEquation {
    fun fuelRequirementsForMass(masses: List<Int>): Int {
        return masses.stream()
            .mapToInt(Int::toInt)
            .map { it / 3 }
            .map { it - 2 }
            .sum()
    }

    fun totalFuelRequirements(masses: List<Int>): Int {
        return masses.stream()
            .mapToInt(Int::toInt)
            .map(this::totalFuelRequirement)
            .sum()
    }

    private fun totalFuelRequirement(mass: Int): Int {
        var extraFuel = fuelRequirementForMass(mass)
        var totalFuelRequirement = extraFuel
        while (extraFuel > 0) {
            extraFuel = max(0, fuelRequirementsForMass(listOf(extraFuel)))
            totalFuelRequirement += extraFuel
        }
        return totalFuelRequirement
    }

    private fun fuelRequirementForMass(mass: Int): Int {
        return mass/3 -2
    }
}