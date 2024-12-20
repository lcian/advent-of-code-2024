package solvers.day2

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    companion object {
        fun isSafe(report: List<Int>): Boolean {
            var increasing = true
            var decreasing = true
            var minDelta = Int.MAX_VALUE
            var maxDelta = Int.MIN_VALUE
            for (i in 1..<report.size) {
                if (report[i] <= report[i - 1]) {
                    increasing = false
                }
                if (report[i] >= report[i - 1]) {
                    decreasing = false
                }
                minDelta = min(minDelta, abs(report[i] - report[i - 1]))
                maxDelta = max(maxDelta, abs(report[i] - report[i - 1]))
            }
            val safe = (increasing || decreasing) && (1 <= minDelta) && (maxDelta <= 3)
            return safe
        }
    }

    @Benchmark
    override fun solveProblem1(): String {
        val reports = input.lines().dropLast(1).map { it.trim().split(" ").map { it.toInt() }.toList() }
        var res = 0
        for (report in reports) {
            if (isSafe(report)) {
                res += 1
            }
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val reports = input.lines().dropLast(1).map { it.trim().split(" ").map { it.toInt() }.toList() }
        var res = 0
        for (report in reports) {
            if (isSafe(report)) {
                res += 1
                continue
            }
            for (i in 0..<report.size) {
                val dampenedReport = report.filterIndexed { j, _ -> j != i }
                if (isSafe(dampenedReport)) {
                    res += 1
                    break
                }
            }
        }
        return res.toString()
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}