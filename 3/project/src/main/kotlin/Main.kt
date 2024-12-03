package com.lcian

import java.io.File
import java.io.InputStream

fun solve1(){
    val inputStream: InputStream = File("input.txt").inputStream()
    val text = inputStream.bufferedReader().readText()
    val regex = """mul\((\d){1,3},(\d){1,3}\)""".toRegex()
    val results = regex.findAll(text)
    var res = 0
    for (result in results) {
        var s = result.groups.get(0)!!.value
        s = s.replace("""mul\(""".toRegex(), "")
        s = s.replace("""\)""".toRegex(), "")
        res += s.split(',').map(String::toInt).reduce { acc, x -> acc * x }
    }
    println(res)
}

fun solve2(){
    val inputStream: InputStream = File("input.txt").inputStream()
    val text = inputStream.bufferedReader().readText()
    val regex = """(mul\((\d){1,3},(\d){1,3}\))|(do\(\))|(don't\(\))""".toRegex()
    val results = regex.findAll(text)
    var res = 0
    var enabled = true;
    for (result in results) {
        var s = result.groups.get(0)!!.value
        if (s.contains("don't")) {
            enabled = false;
        } else if (s.contains("do()")) {
            enabled = true;
        } else {
            if (!enabled) {
                continue;
            }
            s = s.replace("""mul\(""".toRegex(), "")
            s = s.replace("""\)""".toRegex(), "")
            res += s.split(',').map(String::toInt).reduce { acc, x -> acc * x }
        }
    }
    println(res)
}


fun main() {
    solve1()
    solve2()
}