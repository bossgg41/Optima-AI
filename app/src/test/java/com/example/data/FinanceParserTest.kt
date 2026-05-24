package com.example.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FinanceParserTest {

    @Test
    fun generateDynamicForecast_emptyCosts() {
        val forecasts = FinanceParser.generateDynamicForecast(emptyList())
        assertEquals(12, forecasts.size)
        // Check first month
        val first = forecasts[0]
        assertEquals("Month 1", first.period)

        // With empty costs, sum of currentSpend is 0, baseScale is 0
        assertTrue(first.lstmForecast > 10.0)
        assertTrue(first.gruForecast > 10.0)
        assertTrue(first.transformerForecast > 10.0)
    }

    @Test
    fun generateDynamicForecast_zeroCost() {
        val costs = listOf(DepartmentCost("Dept", 0.0, 0.0, "Category", "Explanation"))
        val forecasts = FinanceParser.generateDynamicForecast(costs)
        assertEquals(12, forecasts.size)
        val first = forecasts[0]
        // Same expectations as empty costs because sum of currentSpend is 0
        assertTrue(first.lstmForecast > 10.0)
    }

    @Test
    fun generateDynamicForecast_negativeGrowth() {
        val costs = listOf(DepartmentCost("Dept", 1000.0, 800.0, "Category", "Explanation"))
        val forecasts = FinanceParser.generateDynamicForecast(costs, targetGrowthPercent = -50.0)
        assertEquals(12, forecasts.size)
        // Ensure values are still above Math.max bounds
        forecasts.forEach {
            assertTrue(it.lstmForecast >= 10.0)
            assertTrue(it.gruForecast >= 10.0)
            assertTrue(it.transformerForecast >= 10.0)
        }
    }

    @Test
    fun generateDynamicForecast_zeroAddedCapital() {
        val costs = listOf(DepartmentCost("Dept", 1000.0, 800.0, "Category", "Explanation"))
        val forecasts = FinanceParser.generateDynamicForecast(costs, addedCapital = 0.0)
        assertEquals(12, forecasts.size)
    }

    @Test
    fun generateDynamicForecast_extremeNegativeCapital() {
        val costs = listOf(DepartmentCost("Dept", 1000.0, 800.0, "Category", "Explanation"))
        // Use an extremely large negative capital to guarantee the result falls below 10.0
        val forecasts = FinanceParser.generateDynamicForecast(costs, addedCapital = -50000000.0)
        assertEquals(12, forecasts.size)

        forecasts.forEach { point ->
            // They should be clamped to 10.0 by Math.max(10.0, value)
            assertEquals(10.0, point.lstmForecast, 0.01)
            assertEquals(10.0, point.gruForecast, 0.01)
            assertEquals(10.0, point.transformerForecast, 0.01)
            assertEquals(10.0, point.confidenceIntervalMin, 0.01)
        }
    }

    @Test
    fun generateDynamicForecast_zeroRiskTolerance() {
        val costs = listOf(DepartmentCost("Dept", 1000.0, 800.0, "Category", "Explanation"))
        val forecasts = FinanceParser.generateDynamicForecast(costs, riskTolerance = 0.0)
        assertEquals(12, forecasts.size)

        forecasts.forEach { point ->
            // Variance should be 0, so max confidence interval should be equal to lstmForecast
            // min confidence interval is clamped to Math.max(10.0, lstmForecast - variance)
            assertTrue(point.lstmForecast > 10.0)
            assertEquals(point.lstmForecast, point.confidenceIntervalMax, 0.01)
            assertEquals(Math.max(10.0, point.lstmForecast), point.confidenceIntervalMin, 0.01)
        }
    }

    @Test
    fun generateDynamicForecast_extremeRiskTolerance() {
        val costs = listOf(DepartmentCost("Dept", 1000.0, 800.0, "Category", "Explanation"))
        val forecasts = FinanceParser.generateDynamicForecast(costs, riskTolerance = 200.0)
        assertEquals(12, forecasts.size)

        val lastPoint = forecasts.last()
        assertTrue(lastPoint.confidenceIntervalMax > lastPoint.lstmForecast)
        // With riskTolerance = 200.0, variance = lstmModelValue * 2.0 * 1.5 = lstmModelValue * 3.0
        // lstmModelValue - variance = -2.0 * lstmModelValue
        // So confidenceIntervalMin should be clamped to 10.0
        assertEquals(10.0, lastPoint.confidenceIntervalMin, 0.01)
    }
}
