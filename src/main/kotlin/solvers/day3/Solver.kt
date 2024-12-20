package solvers.day3

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
        val regex = Regex("""mul\((\d){1,3},(\d){1,3}\)""")
        val matches = regex.findAll(input)
        var res = 0
        for (match in matches) {
            val nums = match.groups.get(0)!!.value
                .replace(Regex("""mul\("""), "")
                .replace(Regex("""\)"""), "")
                .split(',')
                .map(String::toInt)
            res += nums[0] * nums[1]
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val regex = Regex("""(mul\((\d){1,3},(\d){1,3}\))|(do\(\))|(don't\(\))""")
        val matches = regex.findAll(input)
        var res = 0
        var enabled = true
        for (match in matches) {
            var string = match.groups.get(0)!!.value
            if (string.contains("don't")) {
                enabled = false
            } else if (string.contains("do()")) {
                enabled = true
            } else if (enabled) {
                val nums = match.groups.get(0)!!.value
                    .replace(Regex("""mul\("""), "")
                    .replace(Regex("""\)"""), "")
                    .split(',')
                    .map(String::toInt)
                res += nums[0] * nums[1]
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