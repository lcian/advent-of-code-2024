package solvers.day10

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val map = input.lines().dropLast(1)
        fun dfs(i: Int, j: Int, last: Int, visited: MutableSet<Pair<Int, Int>>, peaks: MutableSet<Pair<Int, Int>>) {
            if (Pair(i, j) in visited) return
            if (i !in map.indices || j !in map[i].indices) return
            if (map[i][j].toString().toInt() != last + 1) return
            visited.add(Pair(i, j))
            if (map[i][j] == '9'){
                peaks.add(Pair(i, j))
                return
            }
            dfs(i - 1, j, last + 1, visited, peaks)
            dfs(i + 1, j, last + 1, visited, peaks)
            dfs(i, j - 1, last + 1, visited, peaks)
            dfs(i, j + 1, last + 1, visited, peaks)
        }
        var res = 0L
        for (i in map.indices) {
            for (j in map[i].indices){
                if (map[i][j] == '0') {
                    val peaks: MutableSet<Pair<Int, Int>> = mutableSetOf()
                    dfs(i, j, -1, mutableSetOf(), peaks)
                    res += peaks.size
                }
            }
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val map = input.lines().dropLast(1)
        fun bfs(si: Int, sj: Int): Long {
            var nodes: MutableList<Pair<Int, Int>> = mutableListOf(Pair(si, sj))
            val waysToGet: MutableMap<Pair<Int, Int>, Long> = mutableMapOf(Pair(si, sj) to 1)
            while (nodes.isNotEmpty()) {
                val newNodes: MutableList<Pair<Int, Int>> = mutableListOf()
                for (node in nodes){
                    val (i, j) = node
                    waysToGet[node] = waysToGet.getOrElse(node, { 0L }) + 1
                    if (i + 1 in map.indices && j in map[i].indices && map[i + 1][j].toString().toInt() == map[i][j].toString().toInt() + 1) {
                        newNodes.add(Pair(i + 1, j))
                    }
                    if (i - 1 in map.indices && j in map[i].indices && map[i - 1][j].toString().toInt() == map[i][j].toString().toInt() + 1) {
                        newNodes.add(Pair(i - 1, j))
                    }
                    if (i in map.indices && j + 1 in map[i].indices && map[i][j + 1].toString().toInt() == map[i][j].toString().toInt() + 1) {
                        newNodes.add(Pair(i, j + 1))
                    }
                    if (i in map.indices && j - 1 in map[i].indices && map[i][j - 1].toString().toInt() == map[i][j].toString().toInt() + 1) {
                        newNodes.add(Pair(i, j - 1))
                    }
                }
                nodes = newNodes
            }
            return waysToGet.filter { map[it.key.first][it.key.second] == '9' }.values.reduce(Long::plus)
        }
        var res = 0L
        for (i in map.indices) {
            for (j in map[i].indices){
                if (map[i][j] == '0') {
                    res += bfs(i, j)
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