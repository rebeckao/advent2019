import kotlin.math.abs

class Day3CrossedWires {
    fun manhattanDistanceToClosestIntersection(wire1: List<String>, wire2: List<String>): Int {
        val wire1Points = toPositions(wire1)
        val wire2Points = toPositions(wire2)
        val crossPositions = wire1Points.keys
        crossPositions.retainAll(wire2Points.keys)
        return crossPositions.stream()
            .mapToInt { abs(it.x) + abs(it.y) }
            .min()
            .orElse(0)
    }

    fun wireDistanceToClosestIntersection(wire1: List<String>, wire2: List<String>): Int {
        val wire1Points = toPositions(wire1)
        val wire2Points = toPositions(wire2)
        val crossPositions = wire1Points.keys
        crossPositions.retainAll(wire2Points.keys)
        return crossPositions.stream()
            .mapToInt { wire1Points.getOrDefault(it, 0) + wire2Points.getOrDefault(it, 0) }
            .min()
            .orElse(0)
    }

    private fun toPositions(wirePath: List<String>): HashMap<Position, Int> {
        val wire1Points = HashMap<Position, Int>()
        var current = Position(x = 0, y = 0)
        var travelledDistance = 0
        for (turn in wirePath) {
            val direction = turn[0]
            val steps = turn.substring(1).toInt()
            when (direction) {
                'U' -> {
                    for (y in current.y + 1..current.y + steps) {
                        travelledDistance++
                        wire1Points.putIfAbsent(Position(x = current.x, y = y), travelledDistance)
                    }
                    current = Position(x = current.x, y = current.y + steps)
                }
                'D' -> {
                    for (y in current.y - 1 downTo current.y - steps) {
                        travelledDistance++
                        wire1Points.putIfAbsent(Position(x = current.x, y = y), travelledDistance)
                    }
                    current = Position(x = current.x, y = current.y - steps)
                }
                'R' -> {
                    for (x in current.x + 1..current.x + steps) {
                        travelledDistance++
                        wire1Points.putIfAbsent(Position(x = x, y = current.y), travelledDistance)
                    }
                    current = Position(x = current.x + steps, y = current.y)
                }
                'L' -> {
                    for (x in current.x - 1 downTo current.x - steps) {
                        travelledDistance++
                        wire1Points.putIfAbsent(Position(x = x, y = current.y), travelledDistance)
                    }
                    current = Position(x = current.x - steps, y = current.y)
                }
            }
        }
        return wire1Points
    }

    private data class Position(val x: Int, val y: Int)
}