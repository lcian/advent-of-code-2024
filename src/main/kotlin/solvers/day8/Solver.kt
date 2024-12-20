package solvers.day8

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val lines = input.lines().dropLast(1)
        val positionsMap: MutableMap<Char, MutableSet<Pair<Int, Int>>> = mutableMapOf()
        for (i in lines.indices) {
            for (j in lines[i].indices) {
                if (lines[i][j] != '.') {
                    positionsMap.getOrPut(lines[i][j], { mutableSetOf() }).add(Pair(i, j))
                }
            }
        }
        fun isValid(i: Int, j: Int): Boolean = i in lines.indices && j in lines[i].indices
        val antinodes: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (positions in positionsMap.values) {
            for ((i, j) in positions) {
                for ((ii, jj) in positions) {
                    if (i == ii && j == jj) continue
                    if (i >= ii) continue
                    val di = ii - i
                    val dj = jj - j
                    if (isValid(ii + di, jj + dj)) antinodes.add(Pair(ii + di, jj + dj))
                    if (isValid(i - di, j - dj)) antinodes.add(Pair(i - di, j - dj))
                }
            }
        }
        return antinodes.size.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val lines = input.lines().dropLast(1)
        val positionsMap: MutableMap<Char, MutableSet<Pair<Int, Int>>> = mutableMapOf()
        for (i in lines.indices) {
            for (j in lines[i].indices) {
                if (lines[i][j] != '.') {
                    positionsMap.getOrPut(lines[i][j], { mutableSetOf() }).add(Pair(i, j))
                }
            }
        }
        fun isValid(i: Int, j: Int): Boolean = i in lines.indices && j in lines[i].indices
        val antinodes: MutableSet<Pair<Int, Int>> = mutableSetOf()
        var antennas = 0
        for (frequency in positionsMap.keys) {
            val positions = positionsMap[frequency]!!
            for ((i, j) in positions) {
                for ((ii, jj) in positions) {
                    if (i == ii && j == jj) continue
                    if (i >= ii) continue
                    antinodes.add(Pair(i, j))
                    antinodes.add(Pair(ii, jj))
                    val di = ii - i
                    val dj = jj - j
                    var k = 1
                    while (isValid(ii + di * k, jj + dj * k)) {
                        antinodes.add(Pair(ii + di * k, jj + dj * k))
                        k++
                    }
                    k = 1
                    while (isValid(i - di * k, j - dj * k)) {
                        antinodes.add(Pair(i - di * k, j - dj * k))
                        k++
                    }
                }
            }
        }
        return antinodes.size.toString()
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}