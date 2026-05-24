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
import com.example.ui.components.GuidedOnboardingWizard
import com.example.ui.theme.*
import com.example.ui.components.GuidedOnboardingWizard

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
            GuidedOnboardingWizard(
                isTutorialActive = isTutorialActive,
                tutorialStep = tutorialStep,
                viewModel = viewModel
            )
        }
    }
}
