package solvers.day9

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

data class Block(val type: Type, val id: Int) {
    companion object { enum class Type { EMPTY, FULL } }
}

data class SizedBlock(val type: Type, val id: Int, val size: Int) {
    companion object { enum class Type { EMPTY, FULL } }
}

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val map = input.trim().toCharArray()
        val disk: MutableList<Block> = mutableListOf()
        for (i in map.indices step 2) {
            for (k in 0 until map[i].toString().toInt()) {
                disk.add(Block(Block.Companion.Type.FULL, i / 2))
            }
            if (i + 1 !in map.indices) break
            for (k in 0 until map[i + 1].toString().toInt()) {
                disk.add(Block(Block.Companion.Type.EMPTY, 0))
            }
        }
        var i = 0
        var j = disk.lastIndex
        while (i < j) {
            if (disk[i].type == Block.Companion.Type.FULL) {
                i++
                continue
            }
            if (disk[j].type == Block.Companion.Type.EMPTY) {
                j--
                continue
            }
            disk[i] = disk[j].copy()
            disk[j] = Block(Block.Companion.Type.EMPTY, 0)
            i++
            j--
        }
        val res = disk.takeWhile { it.type == Block.Companion.Type.FULL }
            .mapIndexed { i, x -> i.toLong() * x.id.toLong() }
            .reduce { acc, x -> acc + x }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        val map = input.trim().toCharArray()
        val disk: MutableList<SizedBlock> = mutableListOf()
        for (i in map.indices step 2) {
            disk.add(SizedBlock(SizedBlock.Companion.Type.FULL, i / 2, map[i].toString().toInt()))
            if (i + 1 !in map.indices) break
            disk.add(SizedBlock(SizedBlock.Companion.Type.EMPTY, 0, map[i + 1].toString().toInt()))
        }
        var i = 0
        for (j in disk.lastIndex downTo 0) {
            if (disk[j].type == SizedBlock.Companion.Type.EMPTY) continue
            for (i in 0 until j) {
                if(disk[i].type == SizedBlock.Companion.Type.EMPTY && disk[i].size >= disk[j].size){
                    val rem = disk[i].size - disk[j].size
                    disk[i] = disk[j].copy()
                    disk[j] = SizedBlock(SizedBlock.Companion.Type.EMPTY, 0, disk[j].size)
                    if (rem > 0) disk.add(i + 1, SizedBlock(SizedBlock.Companion.Type.EMPTY, 0, rem))
                    break
                }
            }
        }
        var res = 0L
        var j = 0
        for (i in disk.indices) {
            for (k in 0 until disk[i].size) {
                res += j.toLong() * disk[i].id.toLong()
                j++
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