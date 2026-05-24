package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.FinanceViewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainContentScreen()
            }
        }
    }
}

sealed class ScreenTabs(val index: Int, val label: String, val icon: ImageVector, val tag: String) {
    object Dashboard : ScreenTabs(0, "Dashboard", Icons.Default.Home, "tab_dashboard")
    object DataCore : ScreenTabs(1, "Data Core", Icons.Default.List, "tab_datacore")
    object AnalystAI : ScreenTabs(2, "Analyst AI", Icons.Default.Star, "tab_analyst")
    object ConsultChat : ScreenTabs(3, "Consult Chat", Icons.Default.Email, "tab_chat")
    object HelpHub : ScreenTabs(4, "Help & PDF", Icons.Default.Info, "tab_helphub")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContentScreen() {
    val viewModel: FinanceViewModel = viewModel()
    val selectedTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val isTutorialActive by viewModel.isTutorialActive.collectAsStateWithLifecycle()
    val tutorialStep by viewModel.tutorialStep.collectAsStateWithLifecycle()

    val tabsList = listOf(
        ScreenTabs.Dashboard,
        ScreenTabs.DataCore,
        ScreenTabs.AnalystAI,
        ScreenTabs.ConsultChat,
        ScreenTabs.HelpHub
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "DEEPOPTIMA AI",
                                fontWeight = FontWeight.Bold,
                                color = NeonEmerald,
                                fontSize = 18.sp,
                                letterSpacing = 1.8.sp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "UTC 2026",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SoftGrayText
                                )
                                TextButton(
                                    onClick = { viewModel.restartTutorial() },
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(12.dp), tint = CyberCobalt)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Guide Mode", fontSize = 11.sp, color = CyberCobalt, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
                HorizontalDivider(color = SolidGrayCard, thickness = 1.dp)
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider(color = SolidGrayCard, thickness = 1.dp)
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = SoftGrayText,
                    tonalElevation = 0.dp,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    tabsList.forEach { tab ->
                        NavigationBarItem(
                            selected = selectedTab == tab.index,
                            onClick = { viewModel.setCurrentTab(tab.index) },
                            icon = { Icon(imageVector = tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label, fontSize = 9.sp) },
                            alwaysShowLabel = true,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = NeonEmerald,
                                selectedTextColor = NeonEmerald,
                                unselectedIconColor = SoftGrayText,
                                unselectedTextColor = SoftGrayText,
                                indicatorColor = Color.White
                            ),
                            modifier = Modifier.testTag(tab.tag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // Animated transition between screens
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SpaceDarkBg)
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> DashboardScreen(viewModel = viewModel)
                1 -> DataCoreScreen(viewModel = viewModel)
                2 -> AnalystAiScreen(viewModel = viewModel)
                3 -> ChatScreen(viewModel = viewModel)
                4 -> HelpScreen(viewModel = viewModel)
                else -> DashboardScreen(viewModel = viewModel)
            }

            // --- FLOATING WALKTHROUGH ONBOARDING WIZARD ---
            if (isTutorialActive) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.45f)) // Translucent focus dim
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color(0xFF0F172A)), // Deep Slate Contrast
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(16.dp),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .widthIn(max = 500.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = NeonEmerald,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "GUIDED ONBOARDING",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = NeonEmerald,
                                        letterSpacing = 1.sp
                                    )
                                }
                                Text(
                                    text = "Step ${tutorialStep + 1} of 5",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SoftGrayText
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            val (title, infoText) = when (tutorialStep) {
                                0 -> Pair(
                                    "01. Ingestion of Multi-Format Ledgers",
                                    "Our pipeline processes spreadsheets (CSV/XLSX), PDF invoices, Word DOCX operational reforms, and JPG scans. We use simulated OCR and table cell alignments to standardize parameters. Try uploading one beneath the Data Core tab!"
                                )
                                1 -> Pair(
                                    "02. Interactive AI Forecasting Dashboard",
                                    "Observe predictive GRU (orange) and Transformer (purple) curves calculated on raw cost schemas. Adjust target growth ratios or capital injection levels to run real-world optimizations. Try testing these sliders!"
                                )
                                2 -> Pair(
                                    "03. Role-Based Access Governance (RBAC)",
                                    "For complete enterprise compliance, this suite enforces role access bounds. Choose between ADMIN, AUDITOR, or GUEST in this Help view. GUEST mode locks sliders to prevent analytical modifications."
                                )
                                3 -> Pair(
                                    "04. AI-Powered Portfolio Advisor Audit",
                                    "Go to the Analyst AI screen to analyze high-volatility financial stock lines. Inputs like Capital limits, risk boundaries, and profit objectives are evaluated by ML algorithms to trigger optimal timestamps & trading markets."
                                )
                                4 -> Pair(
                                    "05. Neural Assistant & Programmatic Reports",
                                    "Equipped with deep budget leakage context, the AI Chat answers custom audits and maps operational savings. Finally, export a stylized executive PDF report inside the Help Hub to share data directly!"
                                )
                                else -> Pair("Application Guide", "Discover the core functionalities of DeepOptima.")
                            }

                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = infoText,
                                style = MaterialTheme.typography.bodySmall,
                                color = SoftGrayText,
                                lineHeight = 16.sp,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Indicator Lights
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                for (dot in 0..4) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(4.dp)
                                            .background(
                                                if (dot == tutorialStep) NeonEmerald else Color.White.copy(alpha = 0.2f),
                                                RoundedCornerShape(2.dp)
                                            )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { viewModel.skipTutorial() }) {
                                    Text("Skip", color = SoftGrayText, fontSize = 12.sp)
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    if (tutorialStep > 0) {
                                        OutlinedButton(
                                            onClick = { viewModel.prevTutorialStep() },
                                            shape = RoundedCornerShape(12.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                        ) {
                                            Text("Back", fontSize = 12.sp)
                                        }
                                    }

                                    Button(
                                        onClick = { viewModel.nextTutorialStep() },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald),
                                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = if (tutorialStep == 4) "Finish Guide" else "Next",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
