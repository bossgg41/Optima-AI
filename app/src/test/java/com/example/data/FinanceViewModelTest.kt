package com.example.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class FinanceViewModelTest {

    private lateinit var viewModel: FinanceViewModel

    @Before
    fun setup() {
        viewModel = FinanceViewModel()
    }

    @Test
    fun testConvertCurrency_USD() {
        viewModel.activeCurrency.value = "USD"
        assertEquals(100.0, viewModel.convertCurrency(100.0), 0.001)
    }

    @Test
    fun testConvertCurrency_EUR() {
        viewModel.activeCurrency.value = "EUR"
        assertEquals(92.0, viewModel.convertCurrency(100.0), 0.001)
    }

    @Test
    fun testConvertCurrency_GBP() {
        viewModel.activeCurrency.value = "GBP"
        assertEquals(79.0, viewModel.convertCurrency(100.0), 0.001)
    }

    @Test
    fun testConvertCurrency_INR() {
        viewModel.activeCurrency.value = "INR"
        assertEquals(8320.0, viewModel.convertCurrency(100.0), 0.001)
    }

    @Test
    fun testConvertCurrency_Unknown() {
        viewModel.activeCurrency.value = "XYZ"
        assertEquals(100.0, viewModel.convertCurrency(100.0), 0.001)
    }

    @Test
    fun testFormatCurrency_USD() {
        viewModel.activeCurrency.value = "USD"
        assertEquals("$100", viewModel.formatCurrency(100.0))
        assertEquals("$1,000", viewModel.formatCurrency(1000.0))
    }

    @Test
    fun testFormatCurrency_EUR() {
        viewModel.activeCurrency.value = "EUR"
        assertEquals("€92", viewModel.formatCurrency(100.0))
    }

    @Test
    fun testFormatCurrency_GBP() {
        viewModel.activeCurrency.value = "GBP"
        assertEquals("£79", viewModel.formatCurrency(100.0))
    }

    @Test
    fun testFormatCurrency_INR() {
        viewModel.activeCurrency.value = "INR"
        assertEquals("₹8,320", viewModel.formatCurrency(100.0))
    }

    @Test
    fun testFormatCurrency_Unknown() {
        viewModel.activeCurrency.value = "XYZ"
        assertEquals("$100", viewModel.formatCurrency(100.0))
    }
}
