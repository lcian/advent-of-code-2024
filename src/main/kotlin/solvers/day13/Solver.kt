package solvers.day13

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import space.kscience.kmath.linear.*
import space.kscience.kmath.nd.Structure2D
import space.kscience.kmath.nd.StructureND
import space.kscience.kmath.operations.Ring
import space.kscience.kmath.operations.algebra
import space.kscience.kmath.tensors.api.LinearOpsTensorAlgebra
import kotlin.math.min

typealias Point = Pair<Long, Long>

fun String.toButtonEffect(): Point {
    val effect = this.split(":")[1].split(",").map { it.split("+")[1].toLong() }
    return Point(effect[0], effect[1])
}

fun String.toTarget(): Point {
    val target = this.split(":")[1].split(",").map { it.split("=")[1].toLong() }
    return Point(target[0], target[1])
}

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    companion object {
        fun minimumTokensToWinBruteforce(a: Point, b: Point, target: Point): Long? {
            var res = Float.POSITIVE_INFINITY.toLong()
            for (i in 0..100) {
                for (j in 0..100) {
                    val pos = Pair(i * a.first + j * b.first, i * a.second + j * b.second)
                    if (pos == target) res = min(res, (i * 3 + j).toLong())
                }
            }
            if (res == Float.POSITIVE_INFINITY.toLong()) return null
            return res
        }

        fun minimumTokensToWinCramer(a: Point, b: Point, target: Point): Long? {
            val A = Long.algebra.linearSpace.buildMatrix(2, 3) { row, col ->
                when (row) {
                    0 -> when (col) {
                        0 -> a.first; 1 -> b.first; else -> target.first
                    }

                    else -> when (col) {
                        0 -> a.second; 1 -> b.second; else -> target.second
                    }
                }
            }
            return 0L
        }
    }

    @Benchmark
    override fun solveProblem1(): String {
        val lines = input.lines().dropLast(1)
        var res = 0L
        for (i in 0 until lines.size step 4) {
            val a = lines[i].toButtonEffect()
            val b = lines[i + 1].toButtonEffect()
            val target = lines[i + 2].toTarget()
            val tokens = minimumTokensToWinBruteforce(a, b, target)
            if (tokens != null) {
                res += tokens
            }
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val lines = input.lines().dropLast(1)
        var res = 0L
        for (i in 0 until lines.size step 4) {
            val a = lines[i].toButtonEffect()
            val b = lines[i + 1].toButtonEffect()
            val target = lines[i + 2].toTarget()
            val tokens = minimumTokensToWinBruteforce(a, b, target)
            if (tokens != null) {
                res += tokens
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