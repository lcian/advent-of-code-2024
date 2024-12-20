package solvers.day17

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import space.kscience.kmath.operations.Float32Field.pow
import space.kscience.kmath.operations.Float64Field.pow
import java.util.PriorityQueue

class Machine(
    var ip: Long,
    val registers: MutableMap<Char, Long> = mutableMapOf('A' to 0, 'B' to 0, 'C' to 0),
    val program: List<Char>,
    val output: MutableList<String> = mutableListOf()
) {
    fun evaluateOperand(operand: Char): Long {
        return when (operand) {
            '4' -> registers['A']!!
            '5' -> registers['B']!!
            '6' -> registers['C']!!
            '7' -> error("invalid operand: $operand")
            else -> "$operand".toLong()
        }
    }

    fun applyOperation(operator: Char, operand: Char) {
        var jumped = false
        when (operator) {
            '0' -> registers['A'] = registers['A']!! / (2.0).pow(evaluateOperand(operand)).toLong()
            '1' -> registers['B'] = registers['B']!!.xor(operand.toString().toLong())
            '2' -> registers['B'] = evaluateOperand(operand) % 8
            '3' -> {
                if (registers['A']!! != 0L) {
                    jumped = true
                    ip = operand.toString().toLong()
                }
            }

            '4' -> {
                evaluateOperand(operand);
                registers['B'] = registers['B']!!.xor(registers['C']!!)
            }

            '5' -> output.add("${evaluateOperand(operand) % 8}")
            '6' -> registers['B'] = registers['A']!! / (2.0).pow(evaluateOperand(operand)).toLong()
            '7' -> registers['C'] = registers['A']!! / (2.0).pow(evaluateOperand(operand)).toLong()
        }
        if (!jumped) ip += 2
    }

    fun run(stopAt: (machine: Machine) -> Boolean = { false }) {
        val visited: MutableSet<Quadruplet> = mutableSetOf()
        while (ip in program.indices && ip + 1 in program.indices) {
            val curr = Quadruplet(ip, registers['A']!!, registers['B']!!, registers['C']!!)
            if (stopAt(this) || curr in visited) return
            visited.add(curr)
            applyOperation(program[ip.toInt()], program[ip.toInt() + 1])
        }
    }
}

data class Quadruplet(val ip: Long, val a: Long, val b: Long, val c: Long) {}

@State(Scope.Benchmark)
class Solver : solvers.Solver {
    @Benchmark
    override fun solveProblem1(): String {
        val machine = Machine(0, mutableMapOf('A' to 66752888, 'B' to 0, 'C' to 0), "2417751703415530".toList())
        machine.run()
        return machine.output.joinToString(separator = ",")
    }

    @Benchmark
    override fun solveProblem2(): String {
        val program = "2417751703415530"
        var a = 2417751703415530L
        while (true) {
            val machine = Machine(0, mutableMapOf('A' to a, 'B' to 0, 'C' to 0), program.toList())
            try {
                machine.run { it.output.size >= program.length }
                val out = machine.output.joinToString(separator = "")
                println("$a -> $out")
                if (out == program) return a.toString()
            } catch (e: Error) {
            }
            a += 1
        }
    }
}

fun main() {
    val solver = Solver()
    println(solver.solveProblem1())
    println(solver.solveProblem2())
}