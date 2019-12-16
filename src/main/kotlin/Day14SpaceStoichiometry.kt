import kotlin.math.ceil

class Day14SpaceStoichiometry {
    val reactionPattern = Regex("([0-9]* [A-Z]*)(, [0-9]* [A-Z]*)* => ([0-9]* [A-Z]*)")
    val chemicalPattern = Regex("(?:, )?([0-9]*) ([A-Z]*)")

    fun oreRequiredForFuel(reactionStrings: List<String>): Int {
        val reactions = reactionStrings
            .map { parseReaction(it) }
            .groupBy { it.output.second }
        return reactions["FUEL"]!!.map { it.cheapestCost(reactions) }.min()!!
    }

    private fun parseReaction(reactionString: String): Reaction {
        val matchedGroups = reactionPattern.matchEntire(reactionString)!!.groupValues
        val output = chemicalPattern.matchEntire(matchedGroups.last())!!.groupValues
        val outputChemical = Pair(output[1].toInt(), output[2])
        val inputs = matchedGroups.subList(1, matchedGroups.size - 1)
        val inputChemicals = inputs
            .filter { it != "" }
            .map { chemicalPattern.matchEntire(it)!!.groupValues }
            .map { Pair(it[1].toInt(), it[2]) }
            .toList()
        return Reaction(inputChemicals, outputChemical)
    }

    data class Reaction(val inputs: List<Pair<Int, String>>, val output: Pair<Int, String>) {
        var cost: Int? = null

        fun cheapestCost(reactions: Map<String, List<Reaction>>): Int {
            if (cost == null) {
                cost = inputs
                    .map { cheapestReaction(it.first, it.second, reactions) }
                    .sum()
            }
            return cost!!
        }

        private fun cheapestReaction(
            quantityRequired: Int,
            chemicalName: String,
            reactions: Map<String, List<Reaction>>
        ): Int {
            if (chemicalName == "ORE") {
                return quantityRequired
            }
            val cheapestReaction = reactions[chemicalName]!!
                .map { costOfRequiredQuantity(it, reactions, quantityRequired) }
                .minBy { it.first }!!
            return cheapestReaction.first
        }

        private fun costOfRequiredQuantity(
            reaction: Reaction,
            reactions: Map<String, List<Reaction>>,
            quantityRequired: Int
        ) : Pair<Int, Int> {
            val quantityProduced = reaction.output.first
            val recipeMultiplier = ceil(quantityRequired * 1.0 / quantityProduced).toInt()
            val leftOver = recipeMultiplier * quantityProduced - quantityRequired
            return Pair(reaction.cheapestCost(reactions) * recipeMultiplier, leftOver)
        }
    }
}