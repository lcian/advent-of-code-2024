package solvers.day19

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import space.kscience.kmath.operations.Float32Field.pow
import space.kscience.kmath.operations.Float64Field.pow
import java.util.PriorityQueue

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val words = input.lines()[0].trim().split(",").map(String::trim).toSet()
        val cache: MutableMap<String, Boolean> = mutableMapOf()
        fun isPossible(s: String): Boolean {
            if (s.isBlank()) return true
            if (s in words) return true
            if (s in cache) return cache[s]!!
            for (i in 1 until s.length - 1) {
                val s1 = s.substring(0, i)
                val s2 = s.substring(i)
                if (isPossible(s1) && isPossible(s2)){
                    cache[s] = true
                    return true
                }
            }
            cache[s] = false
            return false
        }
        var res = 0
        for (target in input.lines().drop(2).dropLast(1)){
            if (isPossible(target)) res += 1
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val words = input.lines()[0].trim().split(",").map(String::trim).toSet()
        val cache: MutableMap<String, Long> = mutableMapOf()
        fun waysToGet(s: String): Long {
            if (s.isBlank()) return 1
            if (s in cache) return cache[s]!!
            cache[s] = 0
            if (s in words) cache[s] = cache[s]!! + 1
            for (i in 1 until s.length - 1) {
                val s1 = s.substring(0, i)
                val s2 = s.substring(i)
                val w1 = waysToGet(s1)
                val w2 = waysToGet(s2)
                if (w1 > 0 && w2 > 0) {
                    cache[s] = cache[s]!! + w1 * w2
                }
            }
            return cache[s]!!
        }
        var res = 0L
        for (target in input.lines().drop(2).dropLast(1)){
            val w = waysToGet(target)
            res += w
            println(w)
        }
        return res.toString()
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}