package solvers.day16

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import java.util.PriorityQueue

enum class Direction { NORTH, EAST, SOUTH, WEST }

inline fun <reified T : Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}

inline fun <reified T : Enum<T>> T.previous(): T {
    val values = enumValues<T>()
    val previousOrdinal = if ((ordinal - 1) < 0) values.size - 1 else (ordinal - 1)
    return values[previousOrdinal]
}

fun Array<CharArray>.locate(target: Char): Pair<Int, Int> {
    for (i in this.indices) {
        if (this[i].find { it == target } != null)
            return Pair(i, this[i].indexOfFirst { it == target })
    }
    error { "Could not find robot starting position (marked by '@') in map" }
}

data class Position(val i: Int, val j: Int, val dir: Direction) {
    fun step(): Pair<Position, Long> {
        var (i, j, d) = this
        when (dir) {
            Direction.NORTH -> i -= 1
            Direction.EAST -> j += 1
            Direction.SOUTH -> i += 1
            Direction.WEST -> j -= 1
        }
        return Pair(Position(i, j, d), 1)
    }

    fun turnClockwise(): Pair<Position, Long> {
        return Pair(Position(i, j, dir.next()), 1000)
    }

    fun turnCounterclockwise(): Pair<Position, Long> {
        return Pair(Position(i, j, dir.previous()), 1000)
    }
}

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val map = input.lines().dropLast(1).map(String::toCharArray).toTypedArray()
        val (si, sj) = map.locate('S')
        val sp = Position(si, sj, Direction.EAST)

        val dist: MutableMap<Position, Long> = mutableMapOf()
        dist[sp] = 0
        val nodes: PriorityQueue<Pair<Long, Position>> = PriorityQueue { a, b -> a.first.compareTo(b.first) }
        nodes.add(Pair(0, sp))

        while (nodes.isNotEmpty()) {
            val (d, node) = nodes.remove()
            if (node in dist.keys && dist[node]!! < d) continue
            val (i, j, dir) = node
            if (map[i][j] == 'E') return d.toString()
            for ((nextNode, cost) in listOf(node.step(), node.turnClockwise(), node.turnCounterclockwise())) {
                if (nextNode.i !in map.indices || nextNode.j !in map[i].indices || map[i][j] == '#') continue
                if (nextNode !in dist || d + cost < dist[nextNode]!!) {
                    dist[nextNode] = d + cost
                    nodes.add(Pair(dist[nextNode]!!, nextNode))
                }
            }
        }

        error { "Should not reach this" }
    }

    @Benchmark
    override fun solveProblem2(): String {
        val best = this.solveProblem1().toLong()

        val map = input.lines().dropLast(1).map(String::toCharArray).toTypedArray()
        val (si, sj) = map.locate('S')
        val sp = Position(si, sj, Direction.EAST)

        val prev: MutableMap<Position, MutableList<Position>> = mutableMapOf()
        val dist: MutableMap<Position, Long> = mutableMapOf()
        dist[sp] = 0
        val nodes: PriorityQueue<Pair<Long, Position>> = PriorityQueue { a, b -> a.first.compareTo(b.first) }
        nodes.add(Pair(0, sp))

        var ei = -1
        var ej = -1
        while (nodes.isNotEmpty()) {
            val (d, node) = nodes.remove()
            if (node in dist.keys && dist[node]!! < d) continue
            val (i, j, dir) = node
            if (map[i][j] == 'E' && d > best) {
                ei = i
                ej = j
                break
            }
            for ((nextNode, cost) in listOf(node.step(), node.turnClockwise(), node.turnCounterclockwise())) {
                if (nextNode.i !in map.indices || nextNode.j !in map[i].indices || map[i][j] == '#') continue
                if (nextNode !in dist || d + cost <= dist[nextNode]!!) {
                    dist[nextNode] = d + cost
                    nodes.add(Pair(dist[nextNode]!!, nextNode))
                    if (nextNode !in dist || d + cost < dist[nextNode]!!) {
                        prev[nextNode] = mutableListOf(node)
                    } else {
                        if (nextNode in prev)
                            prev[nextNode]!!.add(node)
                        else
                            prev[nextNode] = mutableListOf(node)
                    }
                }
            }
        }

        val res: MutableSet<Pair<Int, Int>> = mutableSetOf()
        val visited: MutableSet<Position> = mutableSetOf()
        fun dfs(p: Position) {
            val (i, j, d) = p
            res.add(Pair(i, j))
            if (i == si && j == sj) return
            if (p in visited) return
            visited.add(p)
            for (pp in prev.getOrElse(p) { mutableListOf() }) {
                dfs(pp)
            }
        }
        for (d in Direction.entries) {
            visited.clear()
            dfs(Position(ei, ej, d))
        }
        return res.size.toString()
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}