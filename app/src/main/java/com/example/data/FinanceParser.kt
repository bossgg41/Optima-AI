package com.example.data

import android.util.Log

/**
 * Result wrapper for file parsing and validation operations.
 */
sealed class ParserResult<out T> {
    data class Success<out T>(val data: T) : ParserResult<T>()
    data class Error(val message: String, val details: String? = null) : ParserResult<Nothing>()
}

object FinanceParser {

    /**
     * Parses a raw CSV string representing departmental cost data.
     * Expected columns: Department, CurrentSpend, OptimizedSpend, Category, LeakageExplanation
     */
    fun parseCostCsv(csvText: String): ParserResult<List<DepartmentCost>> {
        if (csvText.isBlank()) {
            return ParserResult.Error("Empty Dataset", "The provided CSV text contains no content.")
        }

        val lines = csvText.lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        if (lines.size < 2) {
            return ParserResult.Error("Incomplete CSV structure", "A valid CSV must contain at least a header and one row.")
        }

        val header = lines.first().split(",")
            .map { it.trim().lowercase().removeSurrounding("\"") }

        // Core required headers validation
        val deptIdx = header.indexOfFirst { it.contains("department") || it.contains("dept") }
        val curIdx = header.indexOfFirst { it.contains("current") || it.contains("spend") || it.contains("now") }
        val optIdx = header.indexOfFirst { it.contains("opt") || it.contains("target") || it.contains("should") }
        val catIdx = header.indexOfFirst { it.contains("category") || it.contains("type") }
        val leakIdx = header.indexOfFirst { it.contains("leak") || it.contains("explan") || it.contains("reason") }

        if (deptIdx == -1 || curIdx == -1 || optIdx == -1) {
            return ParserResult.Error(
                "Missing Essential Columns",
                "Your CSV headers must contain 'Department', 'CurrentSpend', and 'OptimizedSpend'. Got: ${lines.first()}"
            )
        }

        val resultList = mutableListOf<DepartmentCost>()

        for (i in 1 until lines.size) {
            val line = lines[i]
            // Safe split accounting for commas inside quotes
            val parts = splitCsvLine(line)

            if (parts.size <= deptIdx || parts.size <= curIdx || parts.size <= optIdx) {
                return ParserResult.Error(
                    "Malformed CSV Row (Row $i)",
                    "The columns on line ${i+1} do not align with the header definitions.\nRow text: $line"
                )
            }

            val department = parts[deptIdx].removeSurrounding("\"").trim()
            if (department.isEmpty()) {
                return ParserResult.Error("Validation Error", "Row ${i + 1} has an empty Department cell.")
            }

            val currentSpendStr = parts[curIdx].removeSurrounding("\"").trim()
            val currentSpend = currentSpendStr.toDoubleOrNull() ?: return ParserResult.Error(
                "Malformed Numerical Value",
                "Row ${i + 1} CurrentSpend value '$currentSpendStr' is not a valid decimal number."
            )

            val optimizedSpendStr = parts[optIdx].removeSurrounding("\"").trim()
            val optimizedSpend = optimizedSpendStr.toDoubleOrNull() ?: return ParserResult.Error(
                "Malformed Numerical Value",
                "Row ${i + 1} OptimizedSpend value '$optimizedSpendStr' is not a valid decimal number."
            )

            if (optimizedSpend < 0 || currentSpend < 0) {
                return ParserResult.Error("Validation Error", "Spending numbers on Row ${i + 1} cannot be negative values.")
            }

            val category = if (catIdx != -1 && catIdx < parts.size) {
                parts[catIdx].removeSurrounding("\"").trim()
            } else "Operations"

            val leakageExplanation = if (leakIdx != -1 && leakIdx < parts.size) {
                parts[leakIdx].removeSurrounding("\"").trim()
            } else "Inefficient resource bounds and lack of comparative algorithmic tracking."

            resultList.add(
                DepartmentCost(
                    department = department,
                    currentSpend = currentSpend,
                    optimizedSpend = optimizedSpend,
                    category = category.ifEmpty { "General" },
                    leakageExplanation = leakageExplanation.ifEmpty { "Optimizable overhead discovered on comparison." }
                )
            )
        }

        return ParserResult.Success(resultList)
    }

    /**
     * Splits a CSV line safely, keeping intact segments inside double quotes.
     */
    private fun splitCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        var currentToken = StringBuilder()
        var insideQuotes = false

        for (c in line) {
            when (c) {
                '"' -> insideQuotes = !insideQuotes
                ',' -> {
                    if (insideQuotes) {
                        currentToken.append(c)
                    } else {
                        result.add(currentToken.toString())
                        currentToken = StringBuilder()
                    }
                }
                else -> currentToken.append(c)
            }
        }
        result.add(currentToken.toString())
        return result
    }

    /**
     * Generates simulated DL Comparative Forecast Points based on the parsed Cost Data.
     * Uses sin waves with trend factors + user variables (Growth Rate %, Added Budget)
     * to dynamically shape the LSTM, GRU, and Transformer curves authentically.
     */
    fun generateDynamicForecast(
        costs: List<DepartmentCost>,
        targetGrowthPercent: Double = 15.0,
        addedCapital: Double = 100000.0,
        riskTolerance: Double = 20.0
    ): List<DemandForecastPoint> {
        val baseScale = costs.sumOf { it.currentSpend } * 0.08
        val baseTargetModifier = 1.0 + (targetGrowthPercent / 100.0)
        val capitalBoost = addedCapital / 20000.0 // scalability boost

        val result = mutableListOf<DemandForecastPoint>()

        // 12 months timeline
        for (month in 1..12) {
            val periodLabel = "Month $month"
            
            // Historical demand exists for Months 1-6, future models predict all
            val isHistorical = month <= 6
            val seasonality = Math.sin(month.toDouble() * 0.5) * 12.0
            
            val historicalDemandValue = if (isHistorical) {
                baseScale * (1.0 + (month * 0.03)) + seasonality + 100.0
            } else null

            // Neural model representations:
            // 1. LSTM: High-fidelity, smooth capture of long-term seasonality.
            val lstmModelValue = baseScale * (1.0 + (month * 0.045)) * baseTargetModifier + (seasonality * 0.95) + capitalBoost + 120.0
            
            // 2. GRU: Faster response but slightly more reactive/volatile curve.
            val gruNoiseFactor = if (month > 6) (month % 3 - 1) * (riskTolerance * 0.8) else 0.0
            val gruModelValue = baseScale * (1.0 + (month * 0.040)) * baseTargetModifier + (seasonality * 1.1) + gruNoiseFactor + capitalBoost + 115.0
            
            // 3. Transformer: Incorporates rich multi-head attention context of capital boost.
            val transformerBoost = if (month > 6) capitalBoost * 1.3 else capitalBoost * 0.8
            val transformerModelValue = baseScale * (1.0 + (month * 0.051)) * baseTargetModifier + (seasonality * 0.85) + transformerBoost + 135.0

            // Confidence interval scales with risk tolerance
            val variance = (lstmModelValue * (riskTolerance / 100.0)) * (if (month > 6) 1.5 else 0.6)
            
            result.add(
                DemandForecastPoint(
                    period = periodLabel,
                    historicalDemand = historicalDemandValue,
                    lstmForecast = Math.max(10.0, lstmModelValue),
                    gruForecast = Math.max(10.0, gruModelValue),
                    transformerForecast = Math.max(10.0, transformerModelValue),
                    confidenceIntervalMin = Math.max(10.0, lstmModelValue - variance),
                    confidenceIntervalMax = lstmModelValue + variance
                )
            )
        }
        return result
    }
}