package solvers

import org.openjdk.jmh.annotations.*

interface Solver {
    val input: String
        get() {
            val clazz = this.javaClass.toString()
            assert(clazz.matches(Regex("class solvers.day\\d+.Solver"))) { "$clazz fqn should match solvers.day\\d+.Solver" }
            val day = clazz.drop(clazz.indexOf("day") + 3).takeWhile { it != '.' }
            val inputResource = this::class.java.classLoader.getResource("day$day/input.txt")
            checkNotNull(inputResource) { "Input file not found" }
            return inputResource.readText()
        }

    @Benchmark
    fun solveProblem1(): String

    @Benchmark
    fun solveProblem2(): String
}