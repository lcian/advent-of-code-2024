package solvers.day11

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    companion object {
        fun blink (blinks: Int, stones: List<Long>): Long {
            if (blinks == 0) return stones.size.toLong()
            val newStones: MutableList<Long> = mutableListOf()
            for (stone in stones) {
                when {
                    stone == 0L -> newStones.add(1)
                    stone.toString().length % 2 == 0 -> {
                        val stoneString = stone.toString()
                        newStones.add(stoneString.take(stoneString.length / 2).toLong())
                        newStones.add(stoneString.takeLast(stoneString.length / 2).toLong())
                    }
                    else -> newStones.add(stone * 2024)
                }
            }
            return blink(blinks - 1, newStones)
        }

        val cache: MutableMap<Pair<Int, Long>, Long> = mutableMapOf()
        fun blinkDF (blinks: Int, stone: Long): Long {
            if (Pair(blinks, stone) in cache.keys) {
                return cache[Pair(blinks, stone)]!!
            }
            val res = when {
                blinks == 0 -> 1
                stone == 0L -> blinkDF(blinks - 1, 1)
                stone.toString().length % 2 == 1 -> blinkDF(blinks - 1, stone * 2024 )
                else -> {
                    val stoneString = stone.toString()
                    val first = stoneString.take(stoneString.length / 2).toLong()
                    val last = stoneString.takeLast(stoneString.length / 2).toLong()
                    blinkDF(blinks - 1, first) + blinkDF(blinks - 1, last)
                }
            }
            cache[Pair(blinks, stone)] = res
            return res
        }
    }

    @Benchmark
    override fun solveProblem1(): String {
        val stones = input.trim().split(Regex("\\s")).map(String::toLong)
        return blink(25, stones).toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val stones = input.trim().split(Regex("\\s")).map(String::toLong)
        return stones.map { blinkDF(75, it) }.reduce(Long::plus).toString()
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}