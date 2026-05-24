package com.example

import org.junit.Test
import kotlin.system.measureNanoTime

class FinanceViewModelPerformanceTest {

    @Test
    fun benchmarkSplit() {
        val lines = List(100000) { "Staffing Outlay: Current: $4000, Target: $3200" }

        // Warmup
        var sum1 = 0
        for (line in lines) {
            val departmentLabel = line.split(":", ",", "current", "spend", "target", "cur", "opt", "$")[0].trim()
            sum1 += departmentLabel.length
        }

        // Original split
        sum1 = 0
        val time1 = measureNanoTime {
            for (line in lines) {
                val departmentLabel = line.split(":", ",", "current", "spend", "target", "cur", "opt", "$")[0].trim()
                sum1 += departmentLabel.length
            }
        }
        println("Original split time: ${time1 / 1_000_000} ms")

        // New indexOfAny
        val departmentDelimiters = listOf(":", ",", "current", "spend", "target", "cur", "opt", "$")
        var sum3 = 0
        val time3 = measureNanoTime {
            for (line in lines) {
                val idx = line.indexOfAny(departmentDelimiters)
                val departmentLabel = if (idx == -1) line.trim() else line.substring(0, idx).trim()
                sum3 += departmentLabel.length
            }
        }
        println("Optimized indexOfAny time: ${time3 / 1_000_000} ms")
    }
}
