package solvers.day5

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    companion object {
        fun correctUpdate(update: List<Int>, rules: Set<Pair<Int, Int>>): List<Int> {
            val correct = update.sortedWith(Comparator<Int> {
                a, b -> when {
                    Pair(a, b) in rules -> -1
                    Pair(b, a) in rules -> 1
                    else -> 0
                }
            })
            return correct
        }
    }

    @Benchmark
    override fun solveProblem1(): String {
        val lines = input.lines()
        val rules = lines
            .map { it.trim() }
            .takeWhile { it.isNotEmpty() }
            .map { it.split(Regex("\\|"), 2) }
            .map { Pair(it[0].toInt(), it[1].toInt()) }
            .toSet()
        val updates = lines
            .map { it.trim() }
            .dropWhile { it != "" }
            .drop(1)
            .dropLast(1)
            .map { it.split(Regex(",")).map { x -> x.toInt() } }
        var res = 0
        for (update in updates) {
            val correct = correctUpdate(update, rules)
            if (update == correct) res += update[update.size / 2]
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val lines = input.lines()
        val rules = lines
            .map { it.trim() }
            .takeWhile { it.isNotEmpty() }
            .map { it.split(Regex("\\|"), 2) }
            .map { Pair(it[0].toInt(), it[1].toInt()) }
            .toSet()
        val updates = lines
            .map { it.trim() }
            .dropWhile { it != "" }
            .drop(1)
            .dropLast(1)
            .map { it.split(Regex(",")).map { x -> x.toInt() } }
        var res = 0
        for (update in updates) {
            val correct = correctUpdate(update, rules)
            if (update != correct) res += correct[correct.size / 2]
        }
        return res.toString()
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}