package com.example.data

/**
 * Role-Based Access Control configuration for team collaboration.
 */
enum class UserRole(val label: String, val description: String) {
    ADMIN("Client Admin", "Full privileges: Configure neural network weights, edit base budgets, override forecasts."),
    ANALYST("Financial Analyst", "Intermediate privileges: Run forecasting scenarios, upload raw datasets, run validations."),
    GUEST("Reviewer / Guest", "View-only privileges: Inspect interactive visualizers, read reports, chat with AI.")
}

/**
 * Single item of departmental cost for analysis and waste identification.
 */
data class DepartmentCost(
    val department: String,
    val currentSpend: Double,
    val optimizedSpend: Double,
    val category: String, // "Marketing", "SaaS Software", "Payroll", "Travel", "R&D", "Operations"
    val leakageExplanation: String
) {
    val potentialSavings: Double get() = currentSpend - optimizedSpend
    val isWasteful: Boolean get() = potentialSavings > 0.15 * currentSpend
}

/**
 * Data point for Demand Forecasting, comparing historical and deep learning models.
 */
data class DemandForecastPoint(
    val period: String, // "Month 1", "Month 2", etc.
    val historicalDemand: Double?, // Null for future periods
    val lstmForecast: Double,
    val gruForecast: Double,
    val transformerForecast: Double,
    val confidenceIntervalMin: Double,
    val confidenceIntervalMax: Double
)

/**
 * Core active workspace dataset driving all screens reactively.
 */
data class FinancialReport(
    val reportName: String,
    val costs: List<DepartmentCost>,
    val forecasts: List<DemandForecastPoint>
) {
    val totalCurrentSpend: Double get() = costs.sumOf { it.currentSpend }
    val totalOptimizedSpend: Double get() = costs.sumOf { it.optimizedSpend }
    val totalSavings: Double get() = totalCurrentSpend - totalOptimizedSpend
    val wastePercentage: Double get() = if (totalCurrentSpend > 0) (totalSavings / totalCurrentSpend) * 100.0 else 0.0
}

/**
 * Stock or investment portfolio to evaluate using AI market forecasts.
 */
data class UserStock(
    val ticker: String,
    val companyName: String,
    val shares: Double,
    val avgBuyPrice: Double,
    val currentPrice: Double,
    val primaryExchange: String, // "NYSE", "NASDAQ", "Tokyo SE", "London SE", "crypto", etc.
    val confidenceRate: Double = 0.88
) {
    val marketValue: Double get() = shares * currentPrice
    val profitLoss: Double get() = shares * (currentPrice - avgBuyPrice)
    val profitLossPercentage: Double get() = if (avgBuyPrice > 0) ((currentPrice - avgBuyPrice) / avgBuyPrice) * 100.0 else 0.0
}

/**
 * Team member chat message.
 */
data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String, // "User", "AI Assistant", "System"
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
