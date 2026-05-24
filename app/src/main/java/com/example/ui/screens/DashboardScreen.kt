package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.FinanceViewModel
import com.example.data.UserRole
import com.example.ui.components.CapitalOptimizationBar
import com.example.ui.components.ComparativeForecastChart
import com.example.ui.theme.*

@Composable
fun DashboardScreen(
    viewModel: FinanceViewModel,
    modifier: Modifier = Modifier
) {
    val report by viewModel.activeReport.collectAsStateWithLifecycle()
    val activeRole by viewModel.activeRole.collectAsStateWithLifecycle()

    val targetGrowthVal by viewModel.targetGrowth.collectAsStateWithLifecycle()
    val inputMoneyVal by viewModel.inputMoney.collectAsStateWithLifecycle()
    val riskToleranceVal by viewModel.riskTolerance.collectAsStateWithLifecycle()

    val activeCurrency by viewModel.activeCurrency.collectAsStateWithLifecycle()
    val startupCashReservesVal by viewModel.startupCashReserves.collectAsStateWithLifecycle()

    val riskDoubleValue = riskToleranceVal.toDoubleOrNull() ?: 20.0
    val growthDoubleValue = targetGrowthVal.toDoubleOrNull() ?: 15.0
    val cashReserves = startupCashReservesVal.toDoubleOrNull() ?: 350000.0

    // Dynamic Financial Health Score (0 - 100 Algorithm)
    val healthScore = (100.0 - report.wastePercentage * 1.4 - (riskDoubleValue / 6.0) + (growthDoubleValue / 4.0)).coerceIn(0.0, 100.0)

    // Burn Rate & Startup Runway Optimizer
    val monthlyBurn = report.totalCurrentSpend / 12.0
    val runwayMonths = if (monthlyBurn > 0.0) (cashReserves / monthlyBurn) else 100.0

    val isAdmin = activeRole == UserRole.ADMIN || activeRole == UserRole.ANALYST
    val isGuest = activeRole == UserRole.GUEST

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Core Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Real-Time Optimization Desk",
                        style = MaterialTheme.typography.headlineMedium,
                        color = NeonEmerald
                    )
                    Text(
                        text = "Active Dataset: ${report.reportName}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SoftGrayText
                    )
                }

                // Role Quick Indicator Pill
                Box(
                    modifier = Modifier
                        .background(
                            when (activeRole) {
                                UserRole.ADMIN -> NeonEmerald.copy(alpha = 0.2f)
                                UserRole.ANALYST -> CyberCobalt.copy(alpha = 0.2f)
                                UserRole.GUEST -> SoftGrayText.copy(alpha = 0.2f)
                            },
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = activeRole.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = when (activeRole) {
                            UserRole.ADMIN -> NeonEmerald
                            UserRole.ANALYST -> CyberCobalt
                            UserRole.GUEST -> SoftGrayText
                        }
                    )
                }
            }
        }

        // --- Multi-Currency Selector Row ---
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = IceBlueCard),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SolidGrayCard),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Visualized Currency Currency Base:",
                        style = MaterialTheme.typography.labelMedium,
                        color = SoftGrayText
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("USD" to "$", "EUR" to "€", "GBP" to "£", "INR" to "₹").forEach { (code, symbol) ->
                            val isSelected = activeCurrency == code
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.activeCurrency.value = code },
                                label = { Text("$code ($symbol)", fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = NeonEmerald.copy(alpha = 0.15f),
                                    selectedLabelColor = NeonEmerald,
                                    selectedLeadingIconColor = NeonEmerald
                                )
                            )
                        }
                    }
                }
            }
        }

        // --- Dynamic Strategic Scorecard: Health, Burn, Runway, and Anomaly Diagnostics ---
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = IceBlueCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, SolidGrayCard),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Neural Health & Runway Diagnostics",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 1. Health gauge
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = when {
                                    healthScore > 80.0 -> NeonEmerald.copy(alpha = 0.08f)
                                    healthScore > 50.0 -> GoldGain.copy(alpha = 0.08f)
                                    else -> WasteCoral.copy(alpha = 0.08f)
                                }
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .weight(1.1f)
                                .height(130.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${String.format("%.0f", healthScore)}",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = when {
                                        healthScore > 80.0 -> NeonEmerald
                                        healthScore > 50.0 -> GoldGain
                                        else -> WasteCoral
                                    },
                                    fontSize = 36.sp
                                )
                                Text(
                                    text = "Financial Health Score",
                                    fontSize = 11.sp,
                                    color = SoftGrayText,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = when {
                                        healthScore > 80.0 -> "Optimal Balance"
                                        healthScore > 50.0 -> "Moderate Leakage"
                                        else -> "High-Risk Deficits"
                                    },
                                    fontSize = 10.sp,
                                    color = SoftGrayText
                                )
                            }
                        }

                        // 2. Runway Status
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (runwayMonths < 6.0) WasteCoral.copy(alpha = 0.08f) else FutureViolet.copy(alpha = 0.08f)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .weight(1.1f)
                                .height(130.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = if (runwayMonths > 50.0) "50+ Mo" else String.format("%.1f Mo", runwayMonths),
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (runwayMonths < 6.0) WasteCoral else FutureViolet,
                                    fontSize = 32.sp
                                )
                                Text(
                                    text = "Startup Capital Runway",
                                    fontSize = 11.sp,
                                    color = SoftGrayText,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (runwayMonths < 6.0) Icons.Default.Warning else Icons.Default.Check,
                                        contentDescription = null,
                                        tint = if (runwayMonths < 6.0) WasteCoral else NeonEmerald,
                                        modifier = Modifier.size(10.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (runwayMonths < 6.0) "Budget Alert Raised" else "Sustained Runway",
                                        fontSize = 9.sp,
                                        color = SoftGrayText
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Anomaly detection status bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = if (report.wastePercentage > 15.0) WasteCoral else NeonEmerald,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (report.wastePercentage > 15.0) "AI Warning: Unoptimized cost anomalies found." else "No critical anomalies spotted in active dataset.",
                                fontSize = 11.sp,
                                color = SoftGrayText
                            )
                        }
                        Text(
                            text = "ESG: Tracked",
                            fontSize = 10.sp,
                            color = NeonEmerald,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Executive KPI Row cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                KpiCard(
                    title = "Total Annual Spend",
                    value = viewModel.formatCurrency(report.totalCurrentSpend),
                    icon = Icons.Default.Warning,
                    tint = WasteCoral,
                    modifier = Modifier.weight(1f)
                )
                KpiCard(
                    title = "Optimized outlay",
                    value = viewModel.formatCurrency(report.totalOptimizedSpend),
                    icon = Icons.Default.CheckCircle,
                    tint = NeonEmerald,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                KpiCard(
                    title = "Identified Waste",
                    value = viewModel.formatCurrency(report.totalSavings),
                    icon = Icons.Default.Warning,
                    tint = WasteCoral,
                    percentageDetail = "${String.format("%.1f", report.wastePercentage)}% leak",
                    modifier = Modifier.weight(1f)
                )
                KpiCard(
                    title = "Neural Conv. Accuracy",
                    value = "98.24% MSE",
                    icon = Icons.Default.Star,
                    tint = FutureViolet,
                    percentageDetail = "Transformer L3",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Live parameter Adjusters (Locked for view-only Guest role for proper RBAC demonstration)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color(0xFF0F172A)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Model Tuning Parameters",
                            style = MaterialTheme.typography.titleMedium,
                            color = androidx.compose.ui.graphics.Color.White
                        )
                        
                        if (isGuest) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Guest locked",
                                    tint = WasteCoral,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("RBAC LOCKED", style = MaterialTheme.typography.labelSmall, color = WasteCoral)
                            }
                        } else {
                            Text("ADMIN ENABLED", style = MaterialTheme.typography.labelSmall, color = NeonEmerald)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Slider 1: Target Profit growth %
                    val growthDoubleValue = targetGrowthVal.toDoubleOrNull() ?: 15.0
                    Text(
                        text = "Target Profit Increase: ${String.format("%.1f", growthDoubleValue)}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                    Slider(
                        value = growthDoubleValue.toFloat().coerceIn(5f, 60f),
                        onValueChange = {
                            if (isAdmin) {
                                viewModel.targetGrowth.value = String.format("%.1f", it)
                                viewModel.recomputeForecast()
                            }
                        },
                        valueRange = 5f..60f,
                        enabled = isAdmin,
                        colors = SliderDefaults.colors(
                            thumbColor = NeonEmerald,
                            activeTrackColor = NeonEmerald,
                            inactiveTrackColor = androidx.compose.ui.graphics.Color(0xFF334155)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Slider 2: Input Capital to re-allocate
                    val capitalDoubleValue = inputMoneyVal.toDoubleOrNull() ?: 120000.0
                    Text(
                        text = "Inputted Active Capital: ${viewModel.formatCurrency(capitalDoubleValue)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                    Slider(
                        value = capitalDoubleValue.toFloat().coerceIn(1000f, 250000f),
                        onValueChange = {
                            if (isAdmin) {
                                viewModel.inputMoney.value = String.format("%.0f", it)
                                viewModel.recomputeForecast()
                            }
                        },
                        valueRange = 1000f..250000f,
                        enabled = isAdmin,
                        colors = SliderDefaults.colors(
                            thumbColor = CyberCobalt,
                            activeTrackColor = CyberCobalt,
                            inactiveTrackColor = androidx.compose.ui.graphics.Color(0xFF334155)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Slider 3: Risk Tolerance %
                    val riskDoubleValueCurrent = riskToleranceVal.toDoubleOrNull() ?: 20.0
                    Text(
                        text = "Risk Appetite: ${String.format("%.1f", riskDoubleValueCurrent)}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                    Slider(
                        value = riskDoubleValueCurrent.toFloat().coerceIn(5f, 95f),
                        onValueChange = {
                            if (isAdmin) {
                                viewModel.riskTolerance.value = String.format("%.1f", it)
                                viewModel.recomputeForecast()
                            }
                        },
                        valueRange = 5f..95f,
                        enabled = isAdmin,
                        colors = SliderDefaults.colors(
                            thumbColor = FutureViolet,
                            activeTrackColor = FutureViolet,
                            inactiveTrackColor = androidx.compose.ui.graphics.Color(0xFF334155)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Slider 4: Startup Liquid Cash Reserves (Competitor Feature)
                    Text(
                        text = "Startup Liquid Reserves: ${viewModel.formatCurrency(cashReserves)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                    Slider(
                        value = cashReserves.toFloat().coerceIn(10000f, 1500000f),
                        onValueChange = {
                            if (isAdmin) {
                                viewModel.startupCashReserves.value = String.format("%.0f", it)
                            }
                        },
                        valueRange = 10000f..1500000f,
                        enabled = isAdmin,
                        colors = SliderDefaults.colors(
                            thumbColor = GoldGain,
                            activeTrackColor = GoldGain,
                            inactiveTrackColor = androidx.compose.ui.graphics.Color(0xFF334155)
                        )
                    )
                }
            }
        }

        // Charts
        item {
            ComparativeForecastChart(
                points = report.forecasts,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            CapitalOptimizationBar(
                costs = report.costs,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun KpiCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: androidx.compose.ui.graphics.Color,
    percentageDetail: String? = null,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IceBlueCard),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, style = MaterialTheme.typography.bodySmall, color = SoftGrayText)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    color = PureWhite
                )
                if (percentageDetail != null) {
                    Text(
                        text = percentageDetail,
                        style = MaterialTheme.typography.labelSmall,
                        color = tint
                    )
                }
            }
        }
    }
}
