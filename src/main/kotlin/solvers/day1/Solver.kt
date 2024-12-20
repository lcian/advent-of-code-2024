package solvers.day1

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import kotlin.math.abs

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        var lists = input.lines().dropLast(1)
            .map { it.split(Regex(" {3}"), 2) }
            .map { Pair(it.get(0).toInt(), it.get(1).toInt()) }
            .unzip()
        lists = Pair(lists.first.sorted(), lists.second.sorted())
        var res = 0
        for (i in 0..<lists.first.size) {
            res += abs(lists.first[i] - lists.second[i])
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        var lists = input.lines().dropLast(1)
            .map { it.split(Regex(" {3}"), 2) }
            .map { Pair(it.get(0).toInt(), it.get(1).toInt()) }
            .unzip()
        val counter = emptyMap<Int, Int>().toMutableMap()
        for (x in lists.second) {
            counter[x] = counter.getOrDefault(x, 0) + 1
        }
        var res = 0
        for (x in lists.first) {
            res += x * counter.getOrDefault(x, 0)
        }
        return res.toString()
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}