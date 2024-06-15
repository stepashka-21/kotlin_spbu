import kotlin.collections.HashSet
import kotlin.random.Random

class Board(val size: Int) {

    var coordinates: HashSet<Pair<Int, Int>> = HashSet()

    private val tenacious: HashSet<Pair<Int, Int>> = HashSet()
    private val corpse: HashSet<Pair<Int, Int>> = HashSet()

    fun addValue(valueX: Int, valueY: Int) = coordinates.add(Pair(valueX, valueY))

    fun removeValue(valueX: Int, valueY: Int) = coordinates.remove(Pair(valueX, valueY))

    fun hasNext(): Boolean = coordinates.isNotEmpty()

    fun next() {
        for (cX in 0 until size) {
            for (cY in 0 until size) {
                check(Pair(cX, cY))
            }
        }
        for (position in corpse) {
            if (position in coordinates) coordinates.remove(position)
        }
        for (position in tenacious) {
            if (position !in coordinates) coordinates.add(position)
        }
        corpse.clear()
        tenacious.clear()
    }
    fun shuffle() {
        coordinates.clear()
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (Random.nextInt() % 2 == 1) {
                    coordinates.add(Pair(i, j))
                }
            }
        }
    }
    fun clear() {
        coordinates.clear()
    }
    private fun numbersNeighbors(position: Pair<Int, Int>): Int {
        var n = 0
        if (Pair(((position.first - 1) + size) % size, ((position.second) + size)% size) in coordinates) n += 1 // up
        if (Pair(((position.first - 1) + size) % size, ((position.second + 1) + size) % size) in coordinates) n += 1 // rightUp
        if (Pair((position.first + size) % size, ((position.second + 1) + size) % size) in coordinates) n += 1 // right
        if (Pair(((position.first + 1) + size) % size, ((position.second + 1) + size) % size) in coordinates) n += 1 // rightDown
        if (Pair(((position.first + 1) + size) % size, (position.second + size) % size) in coordinates) n += 1 // down
        if (Pair(((position.first + 1) + size) % size, ((position.second - 1) + size) % size) in coordinates) n += 1 // leftDown
        if (Pair((position.first + size) % size, ((position.second - 1) + size) % size) in coordinates) n += 1 // left
        if (Pair(((position.first - 1) + size) % size, ((position.second - 1) + size) % size) in coordinates) n += 1 // leftUp
        return n
    }

    private fun check(position: Pair<Int, Int>) {
        if (position in coordinates) {
            if (numbersNeighbors(position) < 2 || numbersNeighbors(position) > 3) {
                corpse.add(position)
            }

        } else {
            if (numbersNeighbors(position) == 3) {
                tenacious.add(position)
            }
        }
    }
}
