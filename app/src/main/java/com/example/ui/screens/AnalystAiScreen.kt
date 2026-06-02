package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.FinanceViewModel
import com.example.data.UserStock
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalystAiScreen(
    viewModel: FinanceViewModel,
    modifier: Modifier = Modifier
) {
    val rawText by viewModel.rawReportText.collectAsStateWithLifecycle()
    val isReportAnalyzing by viewModel.isReportAnalyzing.collectAsStateWithLifecycle()
    val reportAnalysisResponse by viewModel.reportAnalysisResponse.collectAsStateWithLifecycle()
    val uploadedBitmap by viewModel.uploadedBitmap.collectAsStateWithLifecycle()

    var activeSubTab by remember { mutableStateOf(0) } // 0: Workplace Auditor, 1: Portfolio Risk

    val userStocks by viewModel.userStocks.collectAsStateWithLifecycle()
    val isPortfolioAnalyzing by viewModel.isPortfolioAnalyzing.collectAsStateWithLifecycle()
    val portfolioAnalysisResponse by viewModel.portfolioAnalysisResponse.collectAsStateWithLifecycle()

    val targetGrowth by viewModel.targetGrowth.collectAsStateWithLifecycle()
    val inputMoney by viewModel.inputMoney.collectAsStateWithLifecycle()
    val riskTolerance by viewModel.riskTolerance.collectAsStateWithLifecycle()
    val activeRole by viewModel.activeRole.collectAsStateWithLifecycle()
    val isGuest = activeRole == com.example.data.UserRole.GUEST

    // Add Asset Form States
    var showAddAssetDialog by remember { mutableStateOf(false) }
    val ticker by viewModel.stockTicker.collectAsStateWithLifecycle()
    val compName by viewModel.stockCompany.collectAsStateWithLifecycle()
    val shares by viewModel.stockShares.collectAsStateWithLifecycle()
    val buyPrice by viewModel.stockBuyPrice.collectAsStateWithLifecycle()
    val marketLocale by viewModel.stockMarket.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Toggle tabs
        item {
            TabRow(
                selectedTabIndex = activeSubTab,
                containerColor = IceBlueCard,
                contentColor = NeonEmerald,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[activeSubTab]),
                        color = NeonEmerald
                    )
                }
            ) {
                Tab(
                    selected = activeSubTab == 0,
                    onClick = { activeSubTab = 0 },
                    text = { Text("Workplace Cost Auditor", color = if (activeSubTab == 0) NeonEmerald else SoftGrayText) }
                )
                Tab(
                    selected = activeSubTab == 1,
                    onClick = { activeSubTab = 1 },
                    text = { Text("Trading Portfolio risk", color = if (activeSubTab == 1) NeonEmerald else SoftGrayText) }
                )
            }
        }

        if (activeSubTab == 0) {
            workplaceAuditorTab(
                viewModel = viewModel,
                uploadedBitmap = uploadedBitmap,
                rawText = rawText,
                isReportAnalyzing = isReportAnalyzing,
                reportAnalysisResponse = reportAnalysisResponse
            )
        } else {
            tradingPortfolioTab(
                viewModel = viewModel,
                userStocks = userStocks,
                isGuest = isGuest,
                inputMoney = inputMoney,
                targetGrowth = targetGrowth,
                riskTolerance = riskTolerance,
                isPortfolioAnalyzing = isPortfolioAnalyzing,
                portfolioAnalysisResponse = portfolioAnalysisResponse,
                onAddAssetClick = { showAddAssetDialog = true }
            )
        }
    }

    if (showAddAssetDialog) {
        AddAssetDialog(
            viewModel = viewModel,
            ticker = ticker,
            compName = compName,
            shares = shares,
            buyPrice = buyPrice,
            marketLocale = marketLocale,
            onDismiss = { showAddAssetDialog = false }
        )
    }
}

fun LazyListScope.workplaceAuditorTab(
    viewModel: FinanceViewModel,
    uploadedBitmap: Bitmap?,
    rawText: String,
    isReportAnalyzing: Boolean,
    reportAnalysisResponse: String?
) {
    item {
        Text(
            text = "Workplace Report Processing & Forecast",
            style = MaterialTheme.typography.titleMedium,
            color = PureWhite
        )
        Text(
            text = "Submit a photo report, excel metrics, or general summary of your departmental leakages. The deep models will cross-correlate targets and locate cost leakages.",
            style = MaterialTheme.typography.bodySmall,
            color = SoftGrayText
        )
    }

    // Image Upload Simulator
    item {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = IceBlueCard.copy(alpha = 0.6f)),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (uploadedBitmap != null) Icons.Default.Star else Icons.Default.Search,
                        contentDescription = null,
                        tint = if (uploadedBitmap != null) NeonEmerald else CyberCobalt,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (uploadedBitmap != null) "Workplace_Report_Scan.jpg" else "Upload Workplace Report",
                            style = MaterialTheme.typography.bodyMedium,
                            color = PureWhite
                        )
                        Text(
                            text = if (uploadedBitmap != null) "Base64 payload injected." else "Simulate photo/pdf analyzer scans",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoftGrayText
                        )
                    }
                }

                if (uploadedBitmap != null) {
                    IconButton(onClick = { viewModel.uploadedBitmap.value = null }) {
                        Icon(Icons.Default.Delete, contentDescription = "Clear", tint = WasteCoral)
                    }
                } else {
                    Button(
                        onClick = {
                            // Generate a simulated bitmap graphic containing text parameters representing an expense sheet
                            val simulatedBmp = Bitmap.createBitmap(400, 300, Bitmap.Config.ARGB_8888)
                            val canvas = Canvas(simulatedBmp)
                            val paint = Paint()
                            paint.color = android.graphics.Color.DKGRAY
                            canvas.drawRect(0f, 0f, 400f, 300f, paint)
                            paint.color = android.graphics.Color.GREEN
                            paint.textSize = 24f
                            canvas.drawText("DeepOptima Corporate Audit: Loss identified", 20f, 60f, paint)
                            paint.color = android.graphics.Color.WHITE
                            paint.textSize = 18f
                            canvas.drawText("Marketing Waste: $65,000 keyword leak", 20f, 120f, paint)
                            canvas.drawText("Current Spend: $1.4M", 20f, 180f, paint)
                            canvas.drawText("Desired Growth: 15% rate", 20f, 240f, paint)
                            viewModel.uploadedBitmap.value = simulatedBmp
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberCobalt)
                    ) {
                        Text("Mock Photo")
                    }
                }
            }
        }
    }

    // Text Input Paste Box
    item {
        OutlinedTextField(
            value = rawText,
            onValueChange = { viewModel.rawReportText.value = it },
            label = { Text("Paste Workplace Reports or Ledger Sheets") },
            placeholder = { Text("Department: Executive Marketing\nCurrent Spend: $320k\nLeakage Reasons: Excess manual bidding fees...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonEmerald,
                unfocusedBorderColor = SolidGrayCard,
                focusedLabelColor = NeonEmerald,
                unfocusedLabelColor = SoftGrayText,
                focusedTextColor = PureWhite,
                unfocusedTextColor = PureWhite
            )
        )
    }

    // Run action
    item {
        Button(
            onClick = { viewModel.runWorkplaceReportAnalysis() },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("run_workplace_audit_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = NeonEmerald,
                contentColor = androidx.compose.ui.graphics.Color.White,
                disabledContainerColor = NeonEmerald.copy(alpha = 0.5f),
                disabledContentColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.5f)
            ),
            enabled = !isReportAnalyzing
        ) {
            if (isReportAnalyzing) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = androidx.compose.ui.graphics.Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Running Deep Neural Audit...", color = androidx.compose.ui.graphics.Color.White)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = androidx.compose.ui.graphics.Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Explain Audit & Suggest Actions", color = androidx.compose.ui.graphics.Color.White)
                }
            }
        }
    }

    // Analysis Result Presentation
    reportAnalysisResponse?.let { resp ->
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IceBlueCard),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Face, contentDescription = null, tint = NeonEmerald)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Neural Comparative Diagnostics", style = MaterialTheme.typography.titleMedium, color = NeonEmerald)
                        }

                        IconButton(onClick = { viewModel.rawReportText.value = "" }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = SoftGrayText)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = resp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = PureWhite
                    )
                }
            }
        }
    }
}

fun LazyListScope.tradingPortfolioTab(
    viewModel: FinanceViewModel,
    userStocks: List<UserStock>,
    isGuest: Boolean,
    inputMoney: String,
    targetGrowth: String,
    riskTolerance: String,
    isPortfolioAnalyzing: Boolean,
    portfolioAnalysisResponse: String?,
    onAddAssetClick: () -> Unit
) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "AI-Driven Share / Trading Portfolio",
                    style = MaterialTheme.typography.titleMedium,
                    color = PureWhite
                )
                Text(
                    text = "Manage stocks and run volatility forecasts",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftGrayText
                )
            }

            Button(
                onClick = onAddAssetClick,
                colors = ButtonDefaults.buttonColors(containerColor = CyberCobalt),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Asset")
            }
        }
    }

    // Trading Shares list representation
    if (userStocks.isEmpty()) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = IceBlueCard.copy(alpha = 0.6f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = SoftGrayText,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No Active Assets",
                        style = MaterialTheme.typography.titleMedium,
                        color = PureWhite
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Your trading portfolio is currently empty. Add an asset to begin forecasting.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SoftGrayText,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    } else {
        items(userStocks) { stock ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = IceBlueCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (stock.primaryExchange == "Crypto") FutureViolet.copy(alpha = 0.2f) else CyberCobalt.copy(alpha = 0.2f),
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(stock.ticker, style = MaterialTheme.typography.labelMedium, color = if (stock.primaryExchange == "Crypto") FutureViolet else CyberCobalt)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stock.companyName, style = MaterialTheme.typography.bodyMedium, color = PureWhite)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${stock.shares} units | Purchase avg: $${stock.avgBuyPrice} | Current: $${stock.currentPrice} on ${stock.primaryExchange}",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoftGrayText
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "$${String.format("%,.2f", stock.marketValue)}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = PureWhite
                            )
                            Text(
                                text = if (stock.profitLoss >= 0) "+$${String.format("%.2f", stock.profitLoss)} (${String.format("%.1f", stock.profitLossPercentage)}%)"
                                       else "-$${String.format("%.2f", -stock.profitLoss)} (${String.format("%.1f", stock.profitLossPercentage)}%)",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (stock.profitLoss >= 0) NeonEmerald else WasteCoral
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { viewModel.deleteStock(stock.ticker) }) {
                            Icon(Icons.Default.Close, contentDescription = "Delete", tint = WasteCoral.copy(alpha = 0.7f))
                        }
                    }
                }
            }
        }
    }

    // Interactive Advisor Parameters Card (Tied to user input)
    item {
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = IceBlueCard)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Advisor Target Coefficients",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                    if (isGuest) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = WasteCoral, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Locked (Guest)", fontSize = 10.sp, color = WasteCoral, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Build, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Tuner Active", fontSize = 10.sp, color = NeonEmerald)
                        }
                    }
                }
                Text(
                    text = "Trading predictions and growth compound rates directly reference these parameters.",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftGrayText,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // 1. Capital Allocated Slider
                val currentMoney = inputMoney.toDoubleOrNull() ?: 120000.0
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Incremental Budget Limit:", fontSize = 11.sp, color = SoftGrayText)
                    Text(viewModel.formatCurrency(currentMoney), fontSize = 11.sp, color = PureWhite, fontWeight = FontWeight.Bold)
                }
                Slider(
                    value = currentMoney.toFloat().coerceIn(1000f, 250000f),
                    onValueChange = {
                        if (!isGuest) {
                            viewModel.inputMoney.value = String.format("%.0f", it)
                            viewModel.recomputeForecast()
                        }
                    },
                    valueRange = 1000f..250000f,
                    enabled = !isGuest,
                    colors = SliderDefaults.colors(
                        thumbColor = NeonEmerald,
                        activeTrackColor = NeonEmerald,
                        inactiveTrackColor = SolidGrayCard
                    )
                )

                // 2. Desired Profit Target Slider
                val currentGains = targetGrowth.toDoubleOrNull() ?: 15.0
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Desired Compound Profit margin:", fontSize = 11.sp, color = SoftGrayText)
                    Text("${String.format("%.1f", currentGains)}%", fontSize = 11.sp, color = PureWhite, fontWeight = FontWeight.Bold)
                }
                Slider(
                    value = currentGains.toFloat().coerceIn(5f, 60f),
                    onValueChange = {
                        if (!isGuest) {
                            viewModel.targetGrowth.value = String.format("%.1f", it)
                            viewModel.recomputeForecast()
                        }
                    },
                    valueRange = 5f..60f,
                    enabled = !isGuest,
                    colors = SliderDefaults.colors(
                        thumbColor = NeonEmerald,
                        activeTrackColor = NeonEmerald,
                        inactiveTrackColor = SolidGrayCard
                    )
                )

                // 3. Risk Tolerance Score Slider
                val currentRisk = riskTolerance.toDoubleOrNull() ?: 20.0
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Risk Threshold Tolerance:", fontSize = 11.sp, color = SoftGrayText)
                    Text("${String.format("%.1f", currentRisk)}% Risk Score", fontSize = 11.sp, color = PureWhite, fontWeight = FontWeight.Bold)
                }
                Slider(
                    value = currentRisk.toFloat().coerceIn(5f, 95f),
                    onValueChange = {
                        if (!isGuest) {
                            viewModel.riskTolerance.value = String.format("%.1f", it)
                            viewModel.recomputeForecast()
                        }
                    },
                    valueRange = 5f..95f,
                    enabled = !isGuest,
                    colors = SliderDefaults.colors(
                        thumbColor = NeonEmerald,
                        activeTrackColor = NeonEmerald,
                        inactiveTrackColor = SolidGrayCard
                    )
                )
            }
        }
    }

    // Analyze portfolio trigger
    item {
        Button(
            onClick = { viewModel.runPortfolioAnalysis() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .testTag("run_trading_assessment_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = FutureViolet,
                contentColor = androidx.compose.ui.graphics.Color.White,
                disabledContainerColor = FutureViolet.copy(alpha = 0.5f),
                disabledContentColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.5f)
            ),
            enabled = !isPortfolioAnalyzing
        ) {
            if (isPortfolioAnalyzing) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = androidx.compose.ui.graphics.Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Modeling Global Trends...", color = androidx.compose.ui.graphics.Color.White)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = androidx.compose.ui.graphics.Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ML Portfolio Risk & Action Directives", color = androidx.compose.ui.graphics.Color.White)
                }
            }
        }
    }

    // Portfolio results display
    portfolioAnalysisResponse?.let { resultsText ->
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IceBlueCard),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.List, contentDescription = null, tint = FutureViolet)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Action Plan: Buy / Sell Signals", style = MaterialTheme.typography.titleMedium, color = FutureViolet)
                        }

                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Success model",
                            tint = NeonEmerald
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = resultsText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = PureWhite
                    )
                }
            }
        }
    }
}

@Composable
fun AddAssetDialog(
    viewModel: FinanceViewModel,
    ticker: String,
    compName: String,
    shares: String,
    buyPrice: String,
    marketLocale: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = IceBlueCard,
        title = { Text("Add Portfolio Asset", color = PureWhite) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = ticker,
                    onValueChange = { viewModel.stockTicker.value = it },
                    label = { Text("Ticker (e.g. BTC, AMZN, 7203)") },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberCobalt, unfocusedBorderColor = SolidGrayCard, focusedLabelColor = CyberCobalt, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                )
                OutlinedTextField(
                    value = compName,
                    onValueChange = { viewModel.stockCompany.value = it },
                    label = { Text("Company / Asset description") },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberCobalt, unfocusedBorderColor = SolidGrayCard, focusedLabelColor = CyberCobalt, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = shares,
                        onValueChange = { viewModel.stockShares.value = it },
                        label = { Text("Shares") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberCobalt, unfocusedBorderColor = SolidGrayCard, focusedLabelColor = CyberCobalt, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                    )
                    OutlinedTextField(
                        value = buyPrice,
                        onValueChange = { viewModel.stockBuyPrice.value = it },
                        label = { Text("Buy Price ($)") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberCobalt, unfocusedBorderColor = SolidGrayCard, focusedLabelColor = CyberCobalt, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                    )
                }
                OutlinedTextField(
                    value = marketLocale,
                    onValueChange = { viewModel.stockMarket.value = it },
                    label = { Text("Exchange (NYSE, NASDAQ, Tokyo, Crypto)") },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberCobalt, unfocusedBorderColor = SolidGrayCard, focusedLabelColor = CyberCobalt, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.addStock()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald)
            ) {
                Text("Add Share", color = SpaceDarkBg)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = SoftGrayText)
            }
        }
    )
}
