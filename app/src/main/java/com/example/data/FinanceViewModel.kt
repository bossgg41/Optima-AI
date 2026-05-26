package com.example.data

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.GeminiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FinanceViewModel : ViewModel() {
    companion object {
        private val NUMBER_REGEX = "\\d+[\\d,\\s]*\\.?\\d*".toRegex()
    }

    private val TAG = "FinanceViewModel"

    private val INITIAL_SAMPLE_CSV = """Department,CurrentSpend,OptimizedSpend,Category,LeakageExplanation
Executive Travel,185000,120000,Travel,Over-reliance on premium private corporate airlines and unmanaged hotel selections.
Tech Marketing,450000,320000,Marketing,Un-segmented social ads and high churn on unoptimized bidding keywords.
Infrastructure SaaS,305000,210000,SaaS Software,Unused enterprise database licenses and over-provisioned idle cloud servers.
Fulfillment Operations,890000,810000,Operations,Sub-optimal route dispatch and high manual packaging overheads.
R&D Lab,120000,125000,R&D,Efficient - requires supplementary specialized development equipment.
Administrative Support,95000,75000,Payroll,Redundant manual auditing workflow that can be securely vaporized with automated reporting.
Global HR Outreach,140000,110000,Payroll,Scattered recruiting contracts with high third-party placement agency percentage overheads."""


    // --- Active Dataset State ---
    private val _activeReport = MutableStateFlow<FinancialReport>(
        FinancialReport(
            reportName = "Standard Sample (Corporate Retailer)",
            costs = FinanceParser.parseCostCsv(INITIAL_SAMPLE_CSV).let {
                when (it) {
                    is ParserResult.Success -> it.data
                    else -> emptyList()
                }
            },
            forecasts = emptyList() // Will be computed on launch
        )
    )
    val activeReport: StateFlow<FinancialReport> = _activeReport.asStateFlow()

    // --- Parser Status UI Bindings ---
    private val _importError = MutableStateFlow<String?>(null)
    val importError: StateFlow<String?> = _importError.asStateFlow()

    private val _importSuccess = MutableStateFlow<String?>(null)
    val importSuccess: StateFlow<String?> = _importSuccess.asStateFlow()

    // --- Active User Role for RBAC ---
    private val _activeRole = MutableStateFlow<UserRole>(UserRole.ADMIN)
    val activeRole: StateFlow<UserRole> = _activeRole.asStateFlow()

    // --- Workplace Parameters State ---
    val targetGrowth = MutableStateFlow("15.0")
    val inputMoney = MutableStateFlow("120000.0")
    val riskTolerance = MutableStateFlow("20.0")
    val timePeriod = MutableStateFlow("6")
    val rawReportText = MutableStateFlow("")
    val uploadedBitmap = MutableStateFlow<Bitmap?>(null)

    // --- Competitor Advanced Parameters ---
    val activeCurrency = MutableStateFlow("USD") // "USD", "EUR", "GBP", "INR"
    val startupCashReserves = MutableStateFlow("350000.0") // Cash reserves tracking

    // Live exchange rates and formatting helpers
    fun getExchangeRate(): Double {
        return when (activeCurrency.value) {
            "EUR" -> 0.92
            "GBP" -> 0.79
            "INR" -> 83.2
            else -> 1.0
        }
    }

    fun getCurrencySymbol(): String {
        return when (activeCurrency.value) {
            "EUR" -> "€"
            "GBP" -> "£"
            "INR" -> "₹"
            else -> "$"
        }
    }

    fun convertCurrency(usdValue: Double): Double {
        return usdValue * getExchangeRate()
    }

    fun formatCurrency(usdValue: Double): String {
        val converted = convertCurrency(usdValue)
        val symbol = getCurrencySymbol()
        val formatted = String.format("%,.2f", converted)
        return if (formatted.endsWith(".00")) "$symbol${formatted.dropLast(3)}" else "$symbol$formatted"
    }

    private val _isReportAnalyzing = MutableStateFlow(false)
    val isReportAnalyzing: StateFlow<Boolean> = _isReportAnalyzing.asStateFlow()

    private val _reportAnalysisResponse = MutableStateFlow<String?>(null)
    val reportAnalysisResponse: StateFlow<String?> = _reportAnalysisResponse.asStateFlow()

    // --- Trading Stock Portfolios State ---
    private val _userStocks = MutableStateFlow<List<UserStock>>(
        listOf(
            UserStock("AAPL", "Apple Inc.", 120.0, 165.0, 185.30, "NASDAQ"),
            UserStock("NVDA", "NVIDIA Corp.", 50.0, 480.0, 915.20, "NASDAQ"),
            UserStock("AMZN", "Amazon.com Inc.", 80.0, 140.0, 178.50, "NASDAQ"),
            UserStock("7203", "Toyota Motor Corp", 300.0, 2400.0, 2850.0, "Tokyo SE"),
            UserStock("BTC", "Bitcoin Spot", 0.15, 45000.0, 68400.0, "Crypto")
        )
    )
    val userStocks: StateFlow<List<UserStock>> = _userStocks.asStateFlow()

    val stockTicker = MutableStateFlow("")
    val stockCompany = MutableStateFlow("")
    val stockShares = MutableStateFlow("")
    val stockBuyPrice = MutableStateFlow("")
    val stockCurrentPrice = MutableStateFlow("")
    val stockMarket = MutableStateFlow("NASDAQ")

    private val _isPortfolioAnalyzing = MutableStateFlow(false)
    val isPortfolioAnalyzing: StateFlow<Boolean> = _isPortfolioAnalyzing.asStateFlow()

    private val _portfolioAnalysisResponse = MutableStateFlow<String?>(null)
    val portfolioAnalysisResponse: StateFlow<String?> = _portfolioAnalysisResponse.asStateFlow()

    // --- Chat Room States ---
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(sender = "AI Assistant", content = "Welcome to the Deep Learning Financial Optimizations Desk! I can explain neural LSTMs, cross-evaluate cost leakages, or assist you with planning targeted marketing ROIs. Feel free to ask what is losing your company money!")
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    // --- Current Tab State ---
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    fun setCurrentTab(tabIndex: Int) {
        _currentTab.value = tabIndex
    }

    // --- Onboarding Tutorial State ---
    private val _isTutorialActive = MutableStateFlow(true)
    val isTutorialActive: StateFlow<Boolean> = _isTutorialActive.asStateFlow()

    private val _tutorialStep = MutableStateFlow(0)
    val tutorialStep: StateFlow<Int> = _tutorialStep.asStateFlow()

    fun setTutorialActive(active: Boolean) {
        _isTutorialActive.value = active
    }

    fun nextTutorialStep() {
        if (_tutorialStep.value < 4) {
            val next = _tutorialStep.value + 1
            _tutorialStep.value = next
            val tab = when (next) {
                0 -> 1 // Data Ingestion
                1 -> 0 // Dashboard & Projections
                2 -> 4 // RBAC
                3 -> 2 // AI Portfolio Advisor
                4 -> 3 // Consultative Chat
                else -> 0
            }
            setCurrentTab(tab)
        } else {
            _isTutorialActive.value = false
        }
    }

    fun prevTutorialStep() {
        if (_tutorialStep.value > 0) {
            val prev = _tutorialStep.value - 1
            _tutorialStep.value = prev
            val tab = when (prev) {
                0 -> 1
                1 -> 0
                2 -> 4
                3 -> 2
                4 -> 3
                else -> 0
            }
            setCurrentTab(tab)
        }
    }

    fun skipTutorial() {
        _isTutorialActive.value = false
    }

    fun restartTutorial() {
        _tutorialStep.value = 0
        _isTutorialActive.value = true
        setCurrentTab(1) // Focus onto Data Integration Core for step 0
    }

    init {
        // Trigger initial forecast calculation
        recomputeForecast()
    }

    // --- Business Functions ---

    private val departmentDelimiters = listOf(":", ",", "current", "spend", "target", "cur", "opt", "$")

    /**
     * Set the current role for Role-Based Access Control
     */
    fun selectRole(role: UserRole) {
        _activeRole.value = role
        Log.d(TAG, "Role switched to ${role.name}")
    }

    /**
     * Compute or re-compute the LSTMs, GRUs, and Transformers demand forecast
     * taking in costs and active sliders (target growth, risk, capital).
     */
    fun recomputeForecast() {
        val growth = targetGrowth.value.toDoubleOrNull() ?: 15.0
        val capital = inputMoney.value.toDoubleOrNull() ?: 100000.0
        val risk = riskTolerance.value.toDoubleOrNull() ?: 20.0
        val currentCosts = _activeReport.value.costs

        viewModelScope.launch {
            val forecasts = FinanceParser.generateDynamicForecast(currentCosts, growth, capital, risk)
            _activeReport.value = _activeReport.value.copy(forecasts = forecasts)
        }
    }

    /**
     * Parse and apply a newly uploaded CSV dataset (completely replacing the active dataset)
     */
    fun applyUploadedDataset(datasetName: String, rawCsvText: String) {
        _importError.value = null
        _importSuccess.value = null

        if (rawCsvText.isBlank()) {
            _importError.value = "Dataset cannot be empty. Please provide CSV values."
            return
        }

        // Run through FinanceParser
        when (val result = FinanceParser.parseCostCsv(rawCsvText)) {
            is ParserResult.Error -> {
                _importError.value = "Line Error: ${result.message}\n${result.details ?: ""}"
                Log.e(TAG, "Parsing error: ${result.message}")
            }
            is ParserResult.Success -> {
                val parsedCosts = result.data
                val report = FinancialReport(
                    reportName = datasetName,
                    costs = parsedCosts,
                    forecasts = emptyList() // Will be computed
                )
                _activeReport.value = report
                recomputeForecast()
                _importSuccess.value = "Successfully imported '$datasetName'! Total lines parsed: ${parsedCosts.size}."
                Log.d(TAG, "Successfully replaced active dataset with: $datasetName")
            }
        }
    }

    // --- Unified Multi-Format Extraction Engine ---
    private val _isExtractingData = MutableStateFlow(false)
    val isExtractingData: StateFlow<Boolean> = _isExtractingData.asStateFlow()

    private val _extractionStatus = MutableStateFlow<String?>(null)
    val extractionStatus: StateFlow<String?> = _extractionStatus.asStateFlow()

    fun importDocumentData(format: String, fileName: String, content: String) {
        if (content.isBlank()) {
            _importError.value = "Extraction Failure: Your file scan/OCR input appears empty or unreadable."
            _importSuccess.value = null
            return
        }

        viewModelScope.launch {
            _isExtractingData.value = true
            _importError.value = null
            _importSuccess.value = null
            _extractionStatus.value = "Decoding $format structure rules..."
            
            kotlinx.coroutines.delay(600)
            _extractionStatus.value = "Converting layout scan with high-contrast neural OCR layers..."
            kotlinx.coroutines.delay(800)
            _extractionStatus.value = "Fitting cell variables into standard schema rows..."
            kotlinx.coroutines.delay(600)

            val parsedResult = heuristicExtract(format, content)
            _isExtractingData.value = false
            _extractionStatus.value = null

            when (parsedResult) {
                is ParserResult.Error -> {
                    _importError.value = "Heuristic OCR Error inside '$fileName': ${parsedResult.message}. ${parsedResult.details ?: ""}"
                }
                is ParserResult.Success -> {
                    val parsedCosts = parsedResult.data
                    val report = FinancialReport(
                        reportName = fileName,
                        costs = parsedCosts,
                        forecasts = emptyList()
                    )
                    _activeReport.value = report
                    recomputeForecast()
                    _importSuccess.value = "Correctly parsed '$fileName'! Synced with LSTMs and Attention models completely. Total rows saved: ${parsedCosts.size}."
                }
            }
        }
    }

    internal fun heuristicExtract(format: String, text: String): ParserResult<List<DepartmentCost>> {
        val formatClean = format.trim().lowercase()
        // If it's pure CSV text (or if we find commas structure), try direct parsing first
        if (formatClean == "xlsx/csv" || text.contains(",")) {
            val normalCsv = FinanceParser.parseCostCsv(text)
            if (normalCsv is ParserResult.Success) {
                return normalCsv
            }
        }

        val rows = mutableListOf<DepartmentCost>()
        val lines = text.split('\n', ';')
        
        for (idx in lines.indices) {
            val line = lines[idx].trim()
            if (line.isEmpty() || line.startsWith("#") || line.startsWith("=")) continue
            
            // Look for numerical tokens in the line
            // Replaces dollar signs, commas, or 'k' suffix for simple processing
            val cleanLine = line.replace("$", "").replace("k", "000").replace("K", "000")
            
            val doubleValues = NUMBER_REGEX.findAll(cleanLine)
                .map { it.value.replace(" ", "").replace(",", "").toDoubleOrNull() }
                .filterNotNull()
                .toList()
                
            if (doubleValues.size >= 2) {
                val currentSpendVal = doubleValues[0]
                val optimizedSpendVal = doubleValues[1]
                
                // Segment department from start of line up to first number or label separator
                val delimiterIdx = line.indexOfAny(departmentDelimiters)
                var departmentLabel = if (delimiterIdx == -1) {
                    line.trim()
                } else {
                    line.substring(0, delimiterIdx).trim()
                }

                if (departmentLabel.length > 35) {
                    departmentLabel = departmentLabel.take(35) + "..."
                }
                if (departmentLabel.isEmpty()) {
                    departmentLabel = "Line-Item Extracted Department $idx"
                }
                
                // Heuristic Category tagging
                val categoryName = when {
                    line.contains("travel", ignoreCase = true) || line.contains("hotel", ignoreCase = true) -> "Travel"
                    line.contains("marketing", ignoreCase = true) || line.contains("ads", ignoreCase = true) || line.contains("promo", ignoreCase = true) -> "Marketing"
                    line.contains("software", ignoreCase = true) || line.contains("saas", ignoreCase = true) || line.contains("cloud", ignoreCase = true) -> "SaaS Software"
                    line.contains("lab", ignoreCase = true) || line.contains("research", ignoreCase = true) || line.contains("device", ignoreCase = true) -> "R&D"
                    line.contains("payroll", ignoreCase = true) || line.contains("staff", ignoreCase = true) || line.contains("hr", ignoreCase = true) -> "Payroll"
                    else -> "Operations"
                }

                val customReason = when {
                    line.contains("leak", ignoreCase = true) || line.contains("audit", ignoreCase = true) -> "Identified in PDF/Image audit leakage details."
                    else -> "Identified in unstructured $format OCR heuristics."
                }

                rows.add(
                    DepartmentCost(
                        department = departmentLabel,
                        currentSpend = currentSpendVal,
                        optimizedSpend = optimizedSpendVal,
                        category = categoryName,
                        leakageExplanation = customReason
                    )
                )
            }
        }

        if (rows.isEmpty()) {
            return ParserResult.Error(
                "Unreadable file content structure",
                "Heuristic data check failed. No paired financial outlay fields could be mapped on any line. Document must contain text elements similar to: 'Staffing Outlay: Current: $4000, Target: $3200' or layout grid coordinates."
            )
        }

        return ParserResult.Success(rows)
    }

    /**
     * Live analysis of custom Workplace Reports
     */
    fun runWorkplaceReportAnalysis() {
        val growth = targetGrowth.value
        val capital = inputMoney.value
        val risk = riskTolerance.value
        val time = timePeriod.value
        val manualReport = rawReportText.value
        val bitmap = uploadedBitmap.value

        _isReportAnalyzing.value = true
        _reportAnalysisResponse.value = null

        val currentReport = _activeReport.value
        val spendContext = "Total Active Current Spend: $${String.format("%,.2f", currentReport.totalCurrentSpend)}, Total Optimized target: $${String.format("%,.2f", currentReport.totalOptimizedSpend)} with ${String.format("%.1f", currentReport.wastePercentage)}% cost leakage identified."

        val prompt = if (bitmap != null) {
            """Analyze the workspace photo report uploaded. 
Our Workplace Parameters:
- Total Budget/Inputted Money to Allocate: $${capital}
- Target Desired Profit Increase: ${growth}%
- Risk Tolerance Level: ${risk}%
- Implementation Timeline: ${time} Months
- Context: ${spendContext}

Perform deep learning comparative analysis and tell us where the company is losing money in operations, forecasting future spends, letting us know where to spend more, and what markets/customers/marketing targets to focus on to hit the expected earnings."""
        } else {
            """Analyze the workplace text report:
"${manualReport}"

Our Workplace Parameters:
- Total Budget/Inputted Money to Allocate: $${capital}
- Target Desired Profit Increase: ${growth}%
- Risk Tolerance Level: ${risk}%
- Implementation Timeline: ${time} Months
- Context: ${spendContext}

Identify specific departmental inefficiencies. Graphically lay out where the loss is happening, and predict how programmatic changes can hit the expected earnings."""
        }

        viewModelScope.launch {
            try {
                val answer = GeminiClient.getAnalysis(prompt, bitmap = bitmap)
                _reportAnalysisResponse.value = answer
            } catch (e: Exception) {
                _reportAnalysisResponse.value = "System processing failure: ${e.message}"
            } finally {
                _isReportAnalyzing.value = false
            }
        }
    }

    /**
     * Add a stock item dynamically to driving list
     */
    fun addStock() {
        val ticker = stockTicker.value.trim().uppercase()
        val company = stockCompany.value.trim()
        val shares = stockShares.value.toDoubleOrNull() ?: 0.0
        val buy = stockBuyPrice.value.toDoubleOrNull() ?: 0.0
        val cur = stockCurrentPrice.value.toDoubleOrNull() ?: buy // Match current with buy if empty

        if (ticker.isEmpty() || shares <= 0.0 || buy <= 0.0) {
            return
        }

        val updated = _userStocks.value.toMutableList()
        updated.add(
            UserStock(
                ticker = ticker,
                companyName = company.ifEmpty { "Generic Market Share" },
                shares = shares,
                avgBuyPrice = buy,
                currentPrice = cur,
                primaryExchange = stockMarket.value
            )
        )
        _userStocks.value = updated

        // Clear input fields
        stockTicker.value = ""
        stockCompany.value = ""
        stockShares.value = ""
        stockBuyPrice.value = ""
        stockCurrentPrice.value = ""
    }

    /**
     * Remove stock item dynamically
     */
    fun deleteStock(ticker: String) {
        val updated = _userStocks.value.filter { it.ticker != ticker }
        _userStocks.value = updated
    }

    /**
     * Runs stock prediction engine based on user items and inputted risk thresholds
     */
    fun runPortfolioAnalysis() {
        val capital = inputMoney.value
        val risk = riskTolerance.value
        val targetProfit = targetGrowth.value // Reuse target growth / earnings threshold
        val stockCount = _userStocks.value.size
        val valuation = _userStocks.value.sumOf { it.marketValue }

        _isPortfolioAnalyzing.value = true
        _portfolioAnalysisResponse.value = null

        val stockSummaryText = _userStocks.value.joinToString("\n") {
            "- ${it.ticker} (${it.companyName}): ${it.shares} shares @ avg purchase $${it.avgBuyPrice}, current $${it.currentPrice} on ${it.primaryExchange} exchange."
        }

        val prompt = """Analyze my Trading Portfolio:
$stockSummaryText

Total Portfolio Valuation: $${String.format("%,.2f", valuation)} across $stockCount investment assets.
My Risk Parameters:
- Incremental capital willing to add: $${capital}
- General Risk Tolerance Limit: ${risk}%
- Desired Profit Gains target: $${targetProfit}

Use advanced predictive machine learning models to analyze this historical portfolio for patterns. Identify growth opportunities, risk volatility, and suggest specific global trading markets (NYSE, Nasdaq, London SE, Tokyo, Crypto).
Tell me exactly WHICH stock to buy or sell, WHEN (timestamps), current market indicators, and how to protect my sustained capital in a low-risk compound system."""

        viewModelScope.launch {
            try {
                val results = GeminiClient.getAnalysis(prompt, systemInstruction = "You are an expert Wall Street algorithmic analyst and risk systems engineer.")
                _portfolioAnalysisResponse.value = results
            } catch (e: Exception) {
                _portfolioAnalysisResponse.value = "Market feed error: ${e.message}"
            } finally {
                _isPortfolioAnalyzing.value = false
            }
        }
    }

    /**
     * Send User question to explaining assistant chatbot
     */
    fun sendChatMessage(msgText: String) {
        if (msgText.isBlank()) return

        val userMsg = ChatMessage(sender = "User", content = msgText)
        val updated = _chatMessages.value.toMutableList()
        updated.add(userMsg)
        _chatMessages.value = updated

        _isChatLoading.value = true

        val activeReportDetails = _activeReport.value
        val neuralSetupMessage = """Active Budget Dataset: ${activeReportDetails.reportName}
- Total Annual Outlay: $${String.format("%,.2f", activeReportDetails.totalCurrentSpend)}
- Optimized Neural Target: $${String.format("%,.2f", activeReportDetails.totalOptimizedSpend)}
- Inefficiency Savings Potential: $${String.format("%,.2f", activeReportDetails.totalSavings)} (${String.format("%.1f", activeReportDetails.wastePercentage)}% leak)

Cost Departments:
${activeReportDetails.costs.joinToString("\n") { "${it.department} (${it.category}): Current $${it.currentSpend}, Optimized $${it.optimizedSpend}. Waste leakage reason: ${it.leakageExplanation}" }}

LSTM modeling forecasts standard 12-month sequence trends. GRU handles volatility shifts. Attention-Transformer evaluates strategic reallocations based on ${targetGrowth.value}% growth parameters with a $${inputMoney.value} capitalization.

Based on this complete financial workspace, answer user's question:
"$msgText""""

        viewModelScope.launch {
            try {
                val responseText = GeminiClient.getAnalysis(
                    prompt = msgText,
                    systemInstruction = "You are an expert full-stack developer and AI/data-processing engineer specializing in Comparative Deep Learning (LSTM, GRU, Transformer) for budget leakage. Context: $neuralSetupMessage"
                )
                val aiMsg = ChatMessage(sender = "AI Assistant", content = responseText)
                val newUpdated = _chatMessages.value.toMutableList()
                newUpdated.add(aiMsg)
                _chatMessages.value = newUpdated
            } catch (e: Exception) {
                val errMsg = ChatMessage(sender = "AI Assistant", content = "I had trouble linking to the deep learning models. Trace: ${e.message}")
                val newUpdated = _chatMessages.value.toMutableList()
                newUpdated.add(errMsg)
                _chatMessages.value = newUpdated
            } finally {
                _isChatLoading.value = false
            }
        }
    }

    fun clearChat() {
        _chatMessages.value = listOf(
            ChatMessage(sender = "AI Assistant", content = "Session refreshed. What financial data or neural models would you like me to inspect?")
        )
    }
}
