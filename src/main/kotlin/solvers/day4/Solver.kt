package solvers.day4

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

import java.io.File

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val lines = input.lines()
        var res = 0
        for (i in lines.indices) {
            for (j in lines[i].indices) {
                fun check(dx: Int, dy: Int): Boolean {
                    for (k in 0 until 4) {
                        val x = i + dx * k
                        val y = j + dy * k
                        if (x !in lines.indices || y !in lines[x].indices || lines[x][y] != "XMAS"[k]) {
                            return false
                        }
                    }
                    return true
                }

                if (check(0, 1)) res++
                if (check(0, -1)) res++
                if (check(1, 0)) res++
                if (check(-1, 0)) res++
                if (check(1, 1)) res++
                if (check(-1, -1)) res++
                if (check(1, -1)) res++
                if (check(-1, 1)) res++
            }
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val lines = input.lines().dropLast(1)
        var res = 0
        for (i in 1 until lines.size - 1) {
            for (j in 1 until lines[i].length - 1) {
                val a = lines[i - 1][j - 1].toString()
                val b = lines[i][j].toString()
                val c = lines[i + 1][j + 1].toString()
                val first = (a + b + c == "MAS") || (a + b + c == "SAM")

                val d = lines[i + 1][j - 1].toString()
                val e = lines[i][j].toString()
                val f = lines[i - 1][j + 1].toString()
                val second = (d + e + f == "MAS") || (d + e + f == "SAM")

                if (first && second) res++
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