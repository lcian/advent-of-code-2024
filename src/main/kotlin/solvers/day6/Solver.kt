package solvers.day6

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun next(): Direction {
        return entries.toTypedArray()[(ordinal + 1) % entries.size]
    }
}

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val map = input.lines().dropLast(1)
        var start_i = -1
        var start_j = -1
        for (i in 0 until map.size) {
            val j = map[i].indexOf('^')
            if (j != -1) {
                start_i = i
                start_j = j
                break
            }
        }
        if (start_i !in map.indices || start_j !in map[start_i].indices) {
            error("Something went wrong: cannot find starting character '^' on map")
        }

        val visited = mutableSetOf<Pair<Int, Int>>()
        fun isInside(i: Int, j: Int): Boolean {
            return i >= 0 && j >= 0 && i < map.size && j < map[i].length
        }

        fun isBlocked(i: Int, j: Int): Boolean {
            return isInside(i, j) && map[i][j] == '#'
        }

        fun nextState(i: Int, j: Int, d: Direction): Triple<Int, Int, Direction> {
            val (ii, jj) = when (d) {
                Direction.NORTH -> Pair(i - 1, j)
                Direction.EAST -> Pair(i, j + 1)
                Direction.SOUTH -> Pair(i + 1, j)
                Direction.WEST -> Pair(i, j - 1)
            }
            if (!isBlocked(ii, jj)) return Triple(ii, jj, d)
            return Triple(i, j, d.next())
        }

        var i = start_i
        var j = start_j
        var d = Direction.NORTH
        while (isInside(i, j)) {
            visited.add(Pair(i, j))
            val (ii, jj, dd) = nextState(i, j, d)
            i = ii
            j = jj
            d = dd
        }
        return visited.size.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val map = input.lines().dropLast(1).map { it.toCharArray() }.toList()
        var start_i = -1
        var start_j = -1
        for (i in 0 until map.size) {
            val j = map[i].indexOf('^')
            if (j != -1) {
                start_i = i
                start_j = j
                break
            }
        }
        if (start_i !in map.indices || start_j !in map[start_i].indices) {
            error("Something went wrong: cannot find starting character '^' on map")
        }

        fun isInside(i: Int, j: Int): Boolean {
            return i >= 0 && j >= 0 && i < map.size && j < map[i].size
        }

        fun isBlocked(i: Int, j: Int): Boolean {
            return isInside(i, j) && map[i][j] == '#'
        }

        fun nextState(i: Int, j: Int, d: Direction): Triple<Int, Int, Direction> {
            val (ii, jj) = when (d) {
                Direction.NORTH -> Pair(i - 1, j)
                Direction.EAST -> Pair(i, j + 1)
                Direction.SOUTH -> Pair(i + 1, j)
                Direction.WEST -> Pair(i, j - 1)
            }
            if (!isBlocked(ii, jj)) return Triple(ii, jj, d)
            return Triple(i, j, d.next())
        }

        fun loops(obstacle_i: Int, obstacle_j: Int): Boolean {
            if (obstacle_i == start_i && obstacle_j == start_j) return false
            if (map[obstacle_i][obstacle_j] == '#') return false
            val tmp = map[obstacle_i][obstacle_j]
            map[obstacle_i][obstacle_j] = '#'
            var i = start_i
            var j = start_j
            var d = Direction.NORTH
            var steps = 0
            while (isInside(i, j) && steps < map.size * map[0].size * 4) {
                val (ii, jj, dd) = nextState(i, j, d)
                i = ii
                j = jj
                d = dd
                steps++
            }
            map[obstacle_i][obstacle_j] = tmp
            return steps == map.size * map[0].size * 4
        }

        var res = 0
        for (i in 0 until map.size) {
            for (j in 0 until map[i].size)
                if (loops(i, j)) res++
        }
        return res.toString()
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}