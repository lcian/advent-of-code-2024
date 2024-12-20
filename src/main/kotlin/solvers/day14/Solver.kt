package solvers.day14

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        var quadrantsCount: MutableMap<Int, Int> = mutableMapOf(0 to 0, 1 to 0, 2 to 0, 3 to 0)
        for (line in input.lines().dropLast(1)) {
            val (posList, velList) = line
                .split(Regex(" "), 2)
                .map {
                    it.substring(2)
                        .split(Regex(","), 2)
                        .map(String::toInt)
                }
            var pos = Pair(posList[0], posList[1])
            val vel = Pair(velList[0], velList[1])
            for (i_ in 0 until 100) {
                pos = Pair(
                    (101 + (pos.first + vel.first)) % 101,
                    (103 + (pos.second + vel.second)) % 103,
                )
            }

            if (pos.first == 50 || pos.second == 51) continue
            val left = if (pos.first < 50) 2 else 0
            val up = if (pos.second < 51) 1 else 0
            val quadrant = left + up
            quadrantsCount[quadrant] = quadrantsCount[quadrant]!! + 1
        }
        return quadrantsCount.values.reduce { acc, x -> acc * x }.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val posList: MutableList<Pair<Int, Int>> = mutableListOf()
        val velList: MutableList<Pair<Int, Int>> = mutableListOf()
        for (line in input.lines().dropLast(1)) {
            val (poss, vell) = line
                .split(Regex(" "), 2)
                .map {
                    it.substring(2)
                        .split(Regex(","), 2)
                        .map(String::toInt)
                }
            var pos = Pair(poss[0], poss[1])
            val vel = Pair(vell[0], vell[1])
            posList.add(pos)
            velList.add(vel)
        }

        for (k in 0 until 10000) {
            for (i in posList.indices) {
                val pos = posList[i]
                val vel = velList[i]
                posList[i] = Pair(
                    (101 + (pos.first + vel.first)) % 101,
                    (103 + (pos.second + vel.second)) % 103
                )
            }
            println("########################")
            println(k)
            for (i in 0 until 103) {
                val row = ".".repeat(101).toCharArray()
                for (j in posList.indices) {
                    if (posList[j].second == i) {
                        row[posList[j].first] = 'X'
                    }
                }
                println(row)
            }
            println()
        }
        return ""
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}
