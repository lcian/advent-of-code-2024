package solvers.day20

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

fun Array<CharArray>.locate(target: Char): Pair<Int, Int> {
    for (i in this.indices) {
        if (this[i].find { it == target } != null)
            return Pair(i, this[i].indexOfFirst { it == target })
    }
    error("Could not locate $target in array")
}

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    companion object {
        fun raceDuration(start: Pair<Int, Int>, end: Pair<Int, Int>, map: Array<CharArray>): Long {
            var nodes: MutableList<Pair<Int, Int>> = mutableListOf(start)
            val visited: MutableSet<Pair<Int, Int>> = mutableSetOf(start)
            var d = 0L
            while (nodes.isNotEmpty()){
                val newNodes: MutableList<Pair<Int, Int>> = mutableListOf()
                for ((i, j) in nodes) {
                    if (Pair(i, j) == end) return d
                    for ((ii, jj) in listOf(Pair(i + 1, j), Pair(i - 1, j), Pair(i, j - 1), Pair(i, j + 1))) {
                        if (Pair(ii, jj) in visited) continue
                        if (ii in map.indices && jj in map[ii].indices && map[ii][jj] != '#') {
                            newNodes.add(Pair(ii, jj))
                            visited.add(Pair(ii, jj))
                        }
                    }
                }
                nodes = newNodes
                d += 1
            }
            return Float.POSITIVE_INFINITY.toLong()
        }
    }

    @Benchmark
    override fun solveProblem1(): String {
        val map = input.lines().dropLast(1).map(String::toCharArray).toTypedArray()
        val start = map.locate('S')
        val end = map.locate('E')
        val baseline = raceDuration(start, end, map)
        var res = 0L
        for (i in map.indices){
            println(i)
            for (j in map[i].indices) {
                val tmp1 = map[i][j]
                map[i][j] = '.'
                for (ii in listOf(i - 1, i + 1)) {
                    if (ii !in map.indices) continue
                    val tmp2 = map[ii][j]
                    map[ii][j] = '.'
                    if (raceDuration(start, end, map) <= baseline - 100) res++
                    map[ii][j] = tmp2
                }
                for (jj in listOf(j - 1, j + 1)) {
                    if (jj !in map[i].indices) continue
                    val tmp2 = map[i][jj]
                    map[i][jj] = '.'
                    if (raceDuration(start, end, map) <= baseline - 100) res++
                    map[i][jj] = tmp2
                }
                map[i][j] = tmp1
            }
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        return ""
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}