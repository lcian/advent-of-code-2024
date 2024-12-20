package solvers.day18

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import space.kscience.kmath.operations.Float32Field.pow
import space.kscience.kmath.operations.Float64Field.pow
import java.util.PriorityQueue

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    companion object {
        fun distanceToExit(map: Array<Array<Char>>): Long {
            var nodes: MutableList<Pair<Int, Int>> = mutableListOf(Pair(0, 0))
            val visited: MutableSet<Pair<Int, Int>> = mutableSetOf(Pair(0, 0))
            var d = 0L
            while (nodes.isNotEmpty()){
                val newNodes: MutableList<Pair<Int, Int>> = mutableListOf()
                for ((i, j) in nodes) {
                    if (i == 70 && j == 70) return d
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
        val corrupted = input.lines().take(1024).map { it.trim().split(",") }.map { Pair(it[0].toInt(), it[1].toInt()) }
        val mapList: MutableList<Array<Char>> = mutableListOf()
        for (i in 0 .. 70) mapList.add(".".repeat(71).toCharArray().toTypedArray())
        val map = mapList.toTypedArray()
        for ((i, j) in corrupted) {
            map[i][j] = '#'
        }
        return distanceToExit(map).toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val corrupted = input.lines().dropLast(1).map { it.trim().split(",") }.map { Pair(it[0].toInt(), it[1].toInt()) }
        val mapList: MutableList<Array<Char>> = mutableListOf()
        for (i in 0 .. 70) mapList.add(".".repeat(71).toCharArray().toTypedArray())
        val map = mapList.toTypedArray()
        for ((i, j) in corrupted) {
            map[i][j] = '#'
            if (distanceToExit(map) == Float.POSITIVE_INFINITY.toLong()) return "$i,$j"
        }
        error("Should not reach this")
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}