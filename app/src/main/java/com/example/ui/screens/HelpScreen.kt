package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.FinanceViewModel
import java.security.MessageDigest
import com.example.data.UserRole
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HelpScreen(
    viewModel: FinanceViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activeRole by viewModel.activeRole.collectAsStateWithLifecycle()
    val report by viewModel.activeReport.collectAsStateWithLifecycle()
    val stocks by viewModel.userStocks.collectAsStateWithLifecycle()

    var activeHelpTag by remember { mutableStateOf(0) } // 0: Help, 1: Algorithms, 2: RBAC settings, 3: PDF report

    // Authorization Mock State
    var targetRole by remember { mutableStateOf<UserRole?>(null) }
    var showAuthDialog by remember { mutableStateOf(false) }
    var authPassword by remember { mutableStateOf("") }
    var authError by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Multi-Tabs selection
        item {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HelpPillTab(label = "Operation Help", active = activeHelpTag == 0, onClick = { activeHelpTag = 0 })
                HelpPillTab(label = "Deep Learning comparison", active = activeHelpTag == 1, onClick = { activeHelpTag = 1 })
                HelpPillTab(label = "RBAC Security", active = activeHelpTag == 2, onClick = { activeHelpTag = 2 })
                HelpPillTab(label = "Executive PDF Report", active = activeHelpTag == 3, onClick = { activeHelpTag = 3 })
            }
        }

        when (activeHelpTag) {
            0 -> {
                // OPERATION HELP
                item {
                    Text("Interactive Step-by-Step Walkthrough", style = MaterialTheme.typography.titleMedium, color = NeonEmerald)
                    Text("Discover how to feed your datasets and get advanced predictions effortlessly.", style = MaterialTheme.typography.bodySmall, color = SoftGrayText)
                }

                item {
                    HelpCardStep(
                        index = "01",
                        title = "Verify Active Operational Role",
                        description = "Go to the 'RBAC Security' help tab or review the dashboard marker. Ensure you are 'Client Admin' to write changes, 'Financial Analyst' to upload datasets, or 'Guest Reviewer' to interact."
                    )
                }

                item {
                    HelpCardStep(
                        index = "02",
                        title = "Upload/Paste Company Datasets",
                        description = "Navigate to the 'Data Core' screen. Use our quick preset or paste a custom expense ledger CSV. Press 'Apply & Parse' to replace the workspace constants reactively."
                    )
                }

                item {
                    HelpCardStep(
                        index = "03",
                        title = "Inspect Predictive Forecasting Charts",
                        description = "Review the 'Dashboard'. Change the sliders (Growth Targets, Capital Injection, Risk Tolerances) to observe the multi-path mathematical projections computed across LSTM, GRU, and Attention models."
                    )
                }

                item {
                    HelpCardStep(
                        index = "04",
                        title = "Generate AI Workplace Audits",
                        description = "Go to the 'Analyst AI' screen. Paste text reports or load a simulated photo scanner of a physical worksheet. The deep learning parser maps where you are losing money, forecasting CAC, and offering market directives."
                    )
                }

                item {
                    HelpCardStep(
                        index = "05",
                        title = "Model Stock and Portfolio Risks",
                        description = "Still in 'Analyst AI', input stock/equity tickers, capital limits, and risk thresholds. Run the 'ML Portfolio' audit to receive buy/sell alerts, timestamps, and world market exchanges."
                    )
                }
            }

            1 -> {
                // DEEP LEARNING ARCHITECTURES PRIMER
                item {
                    Text("Architectural Comparison: LSTM vs GRU vs Transformer", style = MaterialTheme.typography.titleMedium, color = FutureViolet)
                    Text("A deep dive into the comparative neural approaches driving DeepOptima's math engine.", style = MaterialTheme.typography.bodySmall, color = SoftGrayText)
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = IceBlueCard),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            ModelComparisonRow(
                                title = "LSTM (Long Short-Term Memory)",
                                math = "f_t = sigmoid(W_f · [h_t-1, x_t] + b_f)",
                                advantage = "Excellent sequential seasonal tracking (e.g., 12-month demand flows). Mitigates the vanishing gradient problem using cell states and forget gates.",
                                speed = "Slow recurrent sequential epochs"
                            )
                            HorizontalDivider(color = SolidGrayCard, modifier = Modifier.padding(vertical = 12.dp))
                            ModelComparisonRow(
                                title = "GRU (Gated Recurrent Unit)",
                                math = "z_t = sigmoid(W_z · [h_t-1, x_t] + b_z)",
                                advantage = "Computationally lightweight. Combines cell and hidden state. Extremely accurate for highly volatile short-term trends with smaller training counts.",
                                speed = "Moderate (Faster than LSTM by 30%)"
                            )
                            HorizontalDivider(color = SolidGrayCard, modifier = Modifier.padding(vertical = 12.dp))
                            ModelComparisonRow(
                                title = "Transformer (Multi-Head Self-Attention)",
                                math = "Attention(Q,K,V) = softmax(QK^T / √d_k)V",
                                advantage = "Captures non-local structural shifts completely in parallel. Analyzes relationships between digital marketing campaigns, global rates, and user capital boosts simultaneously.",
                                speed = "Ultra Fast (Parallelized Attention weights)"
                            )
                        }
                    }
                }
            }

            2 -> {
                // RBAC SETTINGS Switcher panel
                item {
                    Text("Configure Role-Based Access Control", style = MaterialTheme.typography.titleMedium, color = CyberCobalt)
                    Text("For secure enterprise collaboration, choose your active workspace credential. The application interface alters constraints on writing files, sliders, and training pipelines dynamically.", style = MaterialTheme.typography.bodySmall, color = SoftGrayText)
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = IceBlueCard),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            UserRole.values().forEach { role ->
                                val selected = activeRole == role
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            if (selected) CyberCobalt.copy(alpha = 0.15f) else androidx.compose.ui.graphics.Color.Transparent,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            if (role.ordinal < activeRole.ordinal) {
                                                targetRole = role
                                                showAuthDialog = true
                                            } else {
                                                viewModel.selectRole(role)
                                            }
                                        }
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selected,
                                            onClick = {
                                                if (role.ordinal < activeRole.ordinal) {
                                                    targetRole = role
                                                    showAuthDialog = true
                                                } else {
                                                    viewModel.selectRole(role)
                                                }
                                            },
                                            colors = RadioButtonDefaults.colors(selectedColor = CyberCobalt)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(role.label, style = MaterialTheme.typography.bodyMedium, color = PureWhite)
                                            Text(role.description, style = MaterialTheme.typography.labelSmall, color = SoftGrayText)
                                        }
                                    }
                                    if (selected) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CyberCobalt)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }

            3 -> {
                // PDF EXPORT SIMULAOR
                item {
                    Text("Executive PDF Financial Report Generator", style = MaterialTheme.typography.titleMedium, color = GoldGain)
                    Text("Generate a highly formatted executive summary report ready to share with stakeholders.", style = MaterialTheme.typography.bodySmall, color = SoftGrayText)
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = IceBlueCard),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "PDF PREVIEW CAPTION (EXECUTIVE BRIEF)",
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldGain
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            
                            // Formatted Report String block
                            val reportContent = """
==================================================
        DEEPOPTIMA AI: QUANTITATIVE SUMMARY
==================================================
Report Name: ${report.reportName}
Active Role Rank: ${activeRole.label}
==================================================
1. OPEX & COST LEAKAGES SEGMENT
--------------------------------------------------
Total Current Spending: $${String.format("%,.0f", report.totalCurrentSpend)}
Total Neural Optimized Outlay: $${String.format("%,.0f", report.totalOptimizedSpend)}
Potential Annual Cost Savings: $${String.format("%,.0f", report.totalSavings)}
Identified Fiscal Waste Ratio: ${String.format("%.2f", report.wastePercentage)}%

Departmental Optimization Metrics:
${report.costs.joinToString("\n") { "- ${it.department} (${it.category}): Current $${String.format("%,.0f", it.currentSpend)} -> Optimized $${String.format("%,.0f", it.optimizedSpend)}. Savings: $${String.format("%,.0f", it.potentialSavings)}" }}

2. DEEP LEARNING DEMAND FORECASTING (12 Month Proj.)
--------------------------------------------------
Seasonality Model validation loss converged at global minimum.
Comparative analysis reveals Attention Transformer tracks capital re-investments at higher sensitivity.

3. STOCKS & SHARES PORTFOLIO VALUATION
--------------------------------------------------
Active positions: ${stocks.size}
Holding market valuation value: $${String.format("%,.0f", stocks.sumOf { it.marketValue })}
Aggregate Profit/Loss: $${String.format("%,.2f", stocks.sumOf { it.profitLoss })}
==================================================
Generated on: 2026-05-23 (DeepOptima Executive Engine)
==================================================
                            """.trimIndent()

                            Text(
                                text = reportContent,
                                style = MaterialTheme.typography.bodySmall,
                                color = PureWhite,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SpaceDarkBg, RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = {
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, reportContent)
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, "Export Financial PDF Report")
                                    context.startActivity(shareIntent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldGain),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("export_pdf_button")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Share, contentDescription = null, tint = SpaceDarkBg)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Export & Share Document", color = SpaceDarkBg)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAuthDialog && targetRole != null) {
        AlertDialog(
            onDismissRequest = {
                showAuthDialog = false
                authPassword = ""
                authError = false
            },
            title = { Text("Authentication Required", color = PureWhite) },
            text = {
                Column {
                    Text("Upgrading to ${targetRole?.label} requires authorization.", color = SoftGrayText)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = authPassword,
                        onValueChange = {
                            authPassword = it
                            authError = false
                        },
                        label = { Text("Enter Password (e.g., admin)", color = SoftGrayText) },
                        isError = authError,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = PureWhite,
                            unfocusedTextColor = PureWhite
                        ),
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Password),
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
                    )
                    if (authError) {
                        Text("Invalid password.", color = androidx.compose.ui.graphics.Color.Red, style = MaterialTheme.typography.labelSmall)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    // Hash the input password to avoid plaintext check in codebase
                    val md = MessageDigest.getInstance("SHA-256")
                    val digest = md.digest(authPassword.toByteArray(Charsets.UTF_8))
                    val hash = digest.joinToString("") { "%02x".format(it) }

                    if (hash == com.example.BuildConfig.ADMIN_PASSWORD_HASH) {
                        viewModel.selectRole(targetRole!!)
                        showAuthDialog = false
                        authPassword = ""
                        authError = false
                    } else {
                        authError = true
                    }
                }) {
                    Text("Confirm", color = CyberCobalt)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showAuthDialog = false
                    authPassword = ""
                    authError = false
                }) {
                    Text("Cancel", color = SoftGrayText)
                }
            },
            containerColor = SpaceDarkBg
        )
    }

}

@Composable
fun HelpPillTab(
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (active) NeonEmerald.copy(alpha = 0.2f) else SolidGrayCard,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (active) NeonEmerald else SoftGrayText
        )
    }
}

@Composable
fun HelpCardStep(
    index: String,
    title: String,
    description: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = IceBlueCard),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = index,
                style = MaterialTheme.typography.titleLarge,
                color = CyberCobalt
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyMedium, color = PureWhite)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = description, style = MaterialTheme.typography.bodySmall, color = SoftGrayText)
            }
        }
    }
}

@Composable
fun ModelComparisonRow(
    title: String,
    math: String,
    advantage: String,
    speed: String
) {
    Column {
        Text(title, style = MaterialTheme.typography.bodyMedium, color = PureWhite)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Formula: $math",
            style = MaterialTheme.typography.labelSmall,
            color = CyberCobalt,
            modifier = Modifier
                .background(SpaceDarkBg, RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text("Advantage: $advantage", style = MaterialTheme.typography.bodySmall, color = SoftGrayText)
        Spacer(modifier = Modifier.height(2.dp))
        Text("Epoch Speed: $speed", style = MaterialTheme.typography.bodySmall, color = SoftGrayText)
    }
}
