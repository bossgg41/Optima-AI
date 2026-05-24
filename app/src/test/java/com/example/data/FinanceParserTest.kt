package com.example.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FinanceParserTest {

    @Test
    fun parseCostCsv_emptyInput_returnsError() {
        val result = FinanceParser.parseCostCsv("   \n  ")
        assertTrue(result is ParserResult.Error)
        val error = result as ParserResult.Error
        assertEquals("Empty Dataset", error.message)
    }

    @Test
    fun parseCostCsv_singleLine_returnsError() {
        val result = FinanceParser.parseCostCsv("Department,CurrentSpend,OptimizedSpend")
        assertTrue(result is ParserResult.Error)
        val error = result as ParserResult.Error
        assertEquals("Incomplete CSV structure", error.message)
    }

    @Test
    fun parseCostCsv_missingEssentialColumns_returnsError() {
        val result = FinanceParser.parseCostCsv("Department,CurrentSpend,Category\nSales,100,Ops")
        assertTrue(result is ParserResult.Error)
        val error = result as ParserResult.Error
        assertEquals("Missing Essential Columns", error.message)
    }

    @Test
    fun parseCostCsv_malformedCsvRow_returnsError() {
        val csvText = "Department,CurrentSpend,OptimizedSpend\nSales"
        val result = FinanceParser.parseCostCsv(csvText)
        assertTrue(result is ParserResult.Error)
        val error = result as ParserResult.Error
        assertEquals("Malformed CSV Row (Row 1)", error.message)
    }

    @Test
    fun parseCostCsv_emptyDepartment_returnsError() {
        val csvText = "Department,CurrentSpend,OptimizedSpend\n,100.0,80.0"
        val result = FinanceParser.parseCostCsv(csvText)
        assertTrue(result is ParserResult.Error)
        val error = result as ParserResult.Error
        assertEquals("Validation Error", error.message)
        assertTrue(error.details?.contains("empty Department cell") == true)
    }

    @Test
    fun parseCostCsv_malformedNumericalValue_returnsError() {
        val csvText = "Department,CurrentSpend,OptimizedSpend\nSales,100.0,invalid"
        val result = FinanceParser.parseCostCsv(csvText)
        assertTrue(result is ParserResult.Error)
        val error = result as ParserResult.Error
        assertEquals("Malformed Numerical Value", error.message)
        assertTrue(error.details?.contains("not a valid decimal number") == true)
    }

    @Test
    fun parseCostCsv_negativeSpendValues_returnsError() {
        val csvText = "Department,CurrentSpend,OptimizedSpend\nSales,-100.0,80.0"
        val result = FinanceParser.parseCostCsv(csvText)
        assertTrue(result is ParserResult.Error)
        val error = result as ParserResult.Error
        assertEquals("Validation Error", error.message)
        assertTrue(error.details?.contains("cannot be negative values") == true)
    }

    @Test
    fun parseCostCsv_validCsv_returnsSuccess() {
        val csvText = """Department,CurrentSpend,OptimizedSpend,Category,LeakageExplanation
Sales,100.0,80.0,Marketing,High travel cost
Engineering,200.0,150.0,R&D,Unused servers"""
        val result = FinanceParser.parseCostCsv(csvText)
        assertTrue(result is ParserResult.Success)
        val success = result as ParserResult.Success
        val costs = success.data
        assertEquals(2, costs.size)

        assertEquals("Sales", costs[0].department)
        assertEquals(100.0, costs[0].currentSpend, 0.001)
        assertEquals(80.0, costs[0].optimizedSpend, 0.001)
        assertEquals("Marketing", costs[0].category)
        assertEquals("High travel cost", costs[0].leakageExplanation)

        assertEquals("Engineering", costs[1].department)
        assertEquals(200.0, costs[1].currentSpend, 0.001)
        assertEquals(150.0, costs[1].optimizedSpend, 0.001)
        assertEquals("R&D", costs[1].category)
        assertEquals("Unused servers", costs[1].leakageExplanation)
    }

    @Test
    fun parseCostCsv_validCsvWithQuotedFields_returnsSuccess() {
        val csvText = """Department,CurrentSpend,OptimizedSpend,Category,LeakageExplanation
"Sales, Global",100.0,80.0,"Marketing, Ads","High travel, unoptimized" """
        val result = FinanceParser.parseCostCsv(csvText)
        assertTrue(result is ParserResult.Success)
        val success = result as ParserResult.Success
        val costs = success.data
        assertEquals(1, costs.size)

        assertEquals("Sales, Global", costs[0].department)
        assertEquals(100.0, costs[0].currentSpend, 0.001)
        assertEquals(80.0, costs[0].optimizedSpend, 0.001)
        assertEquals("Marketing, Ads", costs[0].category)
        assertEquals("High travel, unoptimized", costs[0].leakageExplanation)
    }

    @Test
    fun parseCostCsv_missingOptionalColumns_usesDefaults() {
        val csvText = "Department,CurrentSpend,OptimizedSpend\nSales,100.0,80.0"
        val result = FinanceParser.parseCostCsv(csvText)
        assertTrue(result is ParserResult.Success)
        val success = result as ParserResult.Success
        val costs = success.data
        assertEquals(1, costs.size)

        assertEquals("Sales", costs[0].department)
        assertEquals("Operations", costs[0].category)
        assertEquals("Inefficient resource bounds and lack of comparative algorithmic tracking.", costs[0].leakageExplanation)
    }

    @Test
    fun parseCostCsv_sampleData_returnsSuccess() {
        val result = FinanceParser.parseCostCsv(FinanceParser.SAMPLE_A_CORPORATE)
        assertTrue(result is ParserResult.Success)
        val success = result as ParserResult.Success
        val costs = success.data
        assertEquals(7, costs.size)
        assertEquals("Executive Travel", costs[0].department)
        assertEquals(185000.0, costs[0].currentSpend, 0.001)
        assertEquals(120000.0, costs[0].optimizedSpend, 0.001)
    }
}
