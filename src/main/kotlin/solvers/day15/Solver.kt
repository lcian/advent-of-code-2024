package solvers.day15

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import kotlin.math.max
import kotlin.math.min

enum class Move { UP, RIGHT, DOWN, LEFT }

fun Char.toMove(): Move {
    return when (this) {
        '^' -> Move.UP
        '>' -> Move.RIGHT
        'v' -> Move.DOWN
        '<' -> Move.LEFT
        else -> {
            check(false) { "Unexpected move: $this" }
            Move.UP
        }
    }
}

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val cinput = """##########
#..O..O.O#
#......O.#
#.OO..O.O#
#..O@..O.#
#O#..O...#
#O..O..O.#
#.OO.O.OO#
#....O...#
##########

<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
>^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
"""
        val map = cinput.lines().takeWhile { it.trim().isNotEmpty() }.map(String::toCharArray)
        val moves = cinput.lines().dropLast(1).last().trim().map(Char::toMove)
        println(moves)
        println("START\n")
        var ri = -1
        var rj = -1
        for (i in map.indices) {
            if (map[i].find { it == '@' } != null) {
                ri = i
                rj = map[i].indexOfFirst { it == '@' }
                break
            }
        }
        check(ri != -1 && rj != -1) { "Could not find robot starting position (marked by '@') in map" }
        for (move in moves) {
            var (di, dj) = Pair(0, 0)
            when (move) {
                Move.UP -> di = -1
                Move.RIGHT -> dj = 1
                Move.DOWN -> di = 1
                Move.LEFT -> dj = -1
            }
            var i = ri + di
            var j = rj + dj
            while (map[i][j] == 'O') {
                i += di
                j += dj
            }
            if (map[i][j] == '#') continue
            for (ii in min(i, ri)..max(i, ri)) {
                for (jj in min(j, rj)..max(j, rj)) {
                    map[ii][jj] = 'O'
                }
            }
            map[ri][rj] = '.'
            map[ri + di][rj + dj] = '@'
            ri += di
            rj += dj
        }
        var res = 0L
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 'O') {
                    res += i * 100 + j
                }
            }
        }
        return res.toString()
    }

    @Benchmark
    override fun solveProblem2(): String {
        return ""
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}