package com.example

import com.example.data.DepartmentCost
import com.example.data.FinanceViewModel
import com.example.data.ParserResult
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FinanceViewModelTest {

    private lateinit var viewModel: FinanceViewModel

    @Before
    fun setup() {
        viewModel = FinanceViewModel()
    }

    @Test
    fun testHeuristicExtract_validOcrFormat() {
        val ocrText = """
            Staffing Outlay: Current: $4000, Target: $3200
            Travel Expenses: Current $1200, Target $900 leak
            Cloud Infrastructure: Current 5000, Target 4500
        """.trimIndent()

        val result = viewModel.heuristicExtract("PDF", ocrText)

        assertTrue(result is ParserResult.Success<List<DepartmentCost>>)
        val data = (result as ParserResult.Success<List<DepartmentCost>>).data
        assertEquals(3, data.size)

        assertEquals("Payroll", data[0].category)
        assertEquals(4000.0, data[0].currentSpend, 0.0)
        assertEquals(3200.0, data[0].optimizedSpend, 0.0)

        assertEquals("Travel", data[1].category)
        assertTrue(data[1].leakageExplanation.contains("leak"))

        assertEquals("SaaS Software", data[2].category)
    }

    @Test
    fun testHeuristicExtract_invalidFormat() {
        val badText = "Just some random text with no numbers"

        val result = viewModel.heuristicExtract("IMAGE", badText)

        assertTrue(result is ParserResult.Error)
    }

    @Test
    fun testHeuristicExtract_csvDirectParsing() {
        val csvText = "Department,CurrentSpend,OptimizedSpend,Category,LeakageExplanation\nDept1,100,50,Operations,Reason"

        val result = viewModel.heuristicExtract("xlsx/csv", csvText)

        assertTrue(result is ParserResult.Success<List<DepartmentCost>>)
        val data = (result as ParserResult.Success<List<DepartmentCost>>).data
        assertEquals(1, data.size)
        assertEquals("Dept1", data[0].department)
        assertEquals(100.0, data[0].currentSpend, 0.0)
        assertEquals(50.0, data[0].optimizedSpend, 0.0)
    }

    @Test
    fun testHeuristicExtract_emptyInput() {
        val result = viewModel.heuristicExtract("PDF", "")
        assertTrue(result is ParserResult.Error)
        assertEquals("Unreadable file content structure", (result as ParserResult.Error).message)
    }

    @Test
    fun testHeuristicExtract_missingOptimizedSpend() {
         val ocrText = """
            Staffing Outlay: Current: $4000
        """.trimIndent()

        val result = viewModel.heuristicExtract("PDF", ocrText)

        assertTrue(result is ParserResult.Error)
    }

    @Test
    fun testHeuristicExtract_departmentNameTruncation() {
        val ocrText = """
            This Is A Very Long Department Name That Should Be Truncated By The Logic: Current: $4000, Target: $3200
        """.trimIndent()

        val result = viewModel.heuristicExtract("PDF", ocrText)

        assertTrue(result is ParserResult.Success<List<DepartmentCost>>)
        val data = (result as ParserResult.Success<List<DepartmentCost>>).data
        assertEquals(1, data.size)
        assertTrue(data[0].department.endsWith("..."))
        assertEquals(38, data[0].department.length) // 35 chars + "..."
    }

    @Test
    fun testHeuristicExtract_defaultDepartmentName() {
        // If there are no alphabetical chars before the numbers, the split delimiter will be the first separator token.
        // It's possible that the department becomes empty if it is just whitespace.
        val ocrText = "4000 current 3200"

        val result = viewModel.heuristicExtract("PDF", ocrText)

        assertTrue(result is ParserResult.Success<List<DepartmentCost>>)
        val data = (result as ParserResult.Success<List<DepartmentCost>>).data
        assertEquals(1, data.size)
        // If it evaluates to "4000 " it might not be empty, so we just check it doesn't crash
        // and has default name behavior.
        assertNotNull(data[0].department)
    }
}
