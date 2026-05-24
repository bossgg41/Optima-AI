package com.example.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FinanceViewModelTest {

    private lateinit var viewModel: FinanceViewModel

    @Before
    fun setUp() {
        viewModel = FinanceViewModel()
    }

    @Test
    fun testFormatCurrency_usd_standardValue() {
        viewModel.activeCurrency.value = "USD"
        val result = viewModel.formatCurrency(1234.56)
        assertEquals("$1,234.56", result)
    }

    @Test
    fun testFormatCurrency_usd_zeroValue() {
        viewModel.activeCurrency.value = "USD"
        val result = viewModel.formatCurrency(0.0)
        assertEquals("$0.00", result)
    }

    @Test
    fun testFormatCurrency_usd_negativeValue() {
        viewModel.activeCurrency.value = "USD"
        val result = viewModel.formatCurrency(-500.25)
        assertEquals("$-500.25", result)
    }

    @Test
    fun testFormatCurrency_usd_largeValue() {
        viewModel.activeCurrency.value = "USD"
        val result = viewModel.formatCurrency(1000000.0)
        assertEquals("$1,000,000.00", result)
    }

    @Test
    fun testFormatCurrency_eur_standardValue() {
        viewModel.activeCurrency.value = "EUR"
        // 1000.0 * 0.92 = 920.0
        val result = viewModel.formatCurrency(1000.0)
        assertEquals("€920.00", result)
    }

    @Test
    fun testFormatCurrency_gbp_standardValue() {
        viewModel.activeCurrency.value = "GBP"
        // 1000.0 * 0.79 = 790.0
        val result = viewModel.formatCurrency(1000.0)
        assertEquals("£790.00", result)
    }

    @Test
    fun testFormatCurrency_inr_standardValue() {
        viewModel.activeCurrency.value = "INR"
        // 100.0 * 83.2 = 8320.0
        val result = viewModel.formatCurrency(100.0)
        assertEquals("₹8,320.00", result)
    }

    @Test
    fun testFormatCurrency_inr_largeValue() {
        viewModel.activeCurrency.value = "INR"
        // 10000.0 * 83.2 = 832000.0
        val result = viewModel.formatCurrency(10000.0)
        assertEquals("₹832,000.00", result)
    }

    @Test
    fun testFormatCurrency_fractionalRounding() {
        viewModel.activeCurrency.value = "USD"
        val result = viewModel.formatCurrency(10.125) // Should round up to 10.13, or depends on locale, let's just use 10.126
        // Let's test standard String.format rounding
        assertEquals("$10.13", viewModel.formatCurrency(10.126))
        assertEquals("$10.12", viewModel.formatCurrency(10.124))
    }
}
