package solvers.day12

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val map = input.lines().dropLast(1).map(String::trim)
        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
        fun dfs(i: Int, j: Int): Pair<Int, Int> {
            if (Pair(i, j) in visited) return Pair(0, 0)
            visited.add(Pair(i, j))
            var area = 1
            var perimeter = 0
            for ((ii, jj) in listOf(Pair(i + 1, j), Pair(i - 1, j), Pair(i, j + 1), Pair(i, j - 1))) {
                if (ii !in map.indices || jj !in map[i].indices || map[ii][jj] != map[i][j]) {
                    perimeter += 1
                } else {
                    val (areaRec, perimeterRec) = dfs(ii, jj)
                    area += areaRec
                    perimeter += perimeterRec
                }
            }
            return Pair(area, perimeter)
        }
        var res = 0L
        for (i in map.indices) {
            for (j in map[i].indices) {
                val (area, perimeter) = dfs(i, j)
                res += area.toLong() * perimeter.toLong()
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