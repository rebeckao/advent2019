import kotlin.math.ceil

class Day14SpaceStoichiometry(reactionStrings: List<String>) {
    private val reactionPattern = Regex("(.*) => ([0-9]* [A-Z]*)")
    private val chemicalPattern = Regex("([0-9]*) ([A-Z]*)")
    private val reactions: Map<String, Reaction>

    init {
        reactions = HashMap()
        reactionStrings
            .map { parseReaction(it) }
            .forEach { reactions[it.output.second] = it }
    }

    fun oreRequiredWithLeftovers(): Int {
        val fuelReaction = reactions["FUEL"]!!
        val requirements: MutableMap<String, Int> =
            fuelReaction.inputs.map { Pair(it.second, it.first) }.toMap().toMutableMap()
        val leftovers = HashMap<String, Int>().toMutableMap()
        var sum = 0
        for (chemical in requirements.keys) {
            sum += resolveAndRecordLeftovers(chemical, requirements[chemical]!!, leftovers)
        }
        return sum
    }

    private fun resolveAndRecordLeftovers(
        chemical: String,
        requiredQuantity: Int,
        leftovers: MutableMap<String, Int>
    ): Int {
        var quantity = requiredQuantity
        if (chemical == "ORE") {
            return quantity
        }
        val leftoverChemical = leftovers[chemical]
        if (leftoverChemical != null) {
            if (leftoverChemical > quantity) {
                leftovers[chemical] = leftoverChemical - quantity
                return 0
            } else {
                quantity -= leftoverChemical
                leftovers[chemical] = 0
            }
        }
        val reaction = reactions[chemical]!!
        val reactionMultiplier = resolveReactionMultiplier(reaction, quantity)
        leftovers[chemical] = (leftovers[chemical] ?: 0) + reactionMultiplier * reaction.output.first - quantity
        var sum = 0
        for (input in reaction.inputs) {
            val inputChemical = input.second
            val inputQuantity = input.first
            sum += resolveAndRecordLeftovers(inputChemical, inputQuantity * reactionMultiplier, leftovers)
        }
        return sum
    }

    private fun resolveReactionMultiplier(
        reaction: Reaction,
        requiredQuantity: Int
    ): Int {
        val producedQuantity = reaction.output.first
        return ceil(requiredQuantity * 1.0 / producedQuantity).toInt()
    }

    fun parseReaction(reactionString: String): Reaction {
        val matchedGroups = reactionPattern.matchEntire(reactionString)!!.groupValues
        val output = chemicalPattern.matchEntire(matchedGroups.last())!!.groupValues
        val outputChemical = Pair(output[1].toInt(), output[2])
        val inputs = matchedGroups[1].split(", ")
        val inputChemicals = inputs
            .filter { it != "" }
            .map { chemicalPattern.matchEntire(it)!!.groupValues }
            .map { Pair(it[1].toInt(), it[2]) }
            .toList()
        return Reaction(inputChemicals, outputChemical)
    }

    data class Reaction(val inputs: List<Pair<Int, String>>, val output: Pair<Int, String>)
}