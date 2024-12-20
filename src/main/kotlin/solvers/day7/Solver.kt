package solvers.day7

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    companion object {
        fun canEqual(
            lhs: Long,
            rhs: List<Long>,
            operands: List<(Long, Long) -> Long> = listOf(Long::plus, Long::times)
        ): Boolean {
            fun rec(i: Int, curr: Long): Boolean {
                if (i == rhs.size) return curr == lhs
                for (op in operands) {
                    if (rec(i + 1, op(curr, rhs[i]))) return true
                }
                return false
            }
            return rec(0, 0)
        }
    }

    @Benchmark
    override fun solveProblem1(): String {
        var res = 0L
        for (line in input.lines().dropLast(1)) {
            val pair = line.split(Regex(":"), 2).map(String::trim)
            val lhs = pair[0].toLong()
            val rhs = pair[1].split(Regex(" ")).map(String::toLong)
            if (canEqual(lhs, rhs)) res += lhs
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        var res = 0L
        val operands: List<(Long, Long) -> Long> = listOf(Long::plus, Long::times,
            { a: Long, b: Long -> (a.toString() + b.toString()).toLong() }
        )
        for (line in input.lines().dropLast(1)) {
            val pair = line.split(Regex(":"), 2).map(String::trim)
            val lhs = pair[0].toLong()
            val rhs = pair[1].split(Regex(" ")).map(String::toLong)
            if (canEqual(lhs, rhs, operands)) res += lhs
        }
        return res.toString()
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}