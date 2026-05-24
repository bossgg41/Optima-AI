package com.example.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.FinanceViewModel
import com.example.data.UserRole
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DataCoreScreen(
    viewModel: FinanceViewModel,
    modifier: Modifier = Modifier
) {
    val report by viewModel.activeReport.collectAsStateWithLifecycle()
    val importError by viewModel.importError.collectAsStateWithLifecycle()
    val importSuccess by viewModel.importSuccess.collectAsStateWithLifecycle()
    val activeRole by viewModel.activeRole.collectAsStateWithLifecycle()
    
    // Ingest states
    val isExtracting by viewModel.isExtractingData.collectAsStateWithLifecycle()
    val extractionStatus by viewModel.extractionStatus.collectAsStateWithLifecycle()

    val isGuest = activeRole == UserRole.GUEST

    // Formats: 0: XLSX/CSV, 1: PDF Scanner, 2: Word (DOCX), 3: Image OCR (JPG/PNG)
    var activeFormatIndex by remember { mutableStateOf(0) }
    var manualInputText by remember { mutableStateOf("") }
    var loadedFileName by remember { mutableStateOf("Global_Opex_Report.xlsx") }

    val formatsList = listOf(
        Triple("Spreadsheet", "XLSX/CSV", Icons.Default.List),
        Triple("PDF Document", "PDF Scan", Icons.Default.Info),
        Triple("Word Document", "DOCX Report", Icons.Default.Info),
        Triple("Image OCR", "JPG/PNG", Icons.Default.Add)
    )

    // Preset data triggers
    val spreadsheetPreset = """Department,CurrentSpend,OptimizedSpend,Category,LeakageExplanation
Corporate Events,160000,95000,Operations,Redundant macro caterers and unmanaged hotel selections.
Affiliate Commissions,310000,240000,Marketing,Hollow affiliate referrals with excessive user churn rates.
Cloud Data Center,480000,320000,SaaS Software,Idle development database clusters and un-reclaimed snapshots.
Staff Logistics,230000,160000,Travel,Premium business flights booked less than 3 days prior.
Social Hype Ads,190000,110000,Marketing,Low CTR influencer campaigns with zero conversions."""

    val pdfPreset = """PDF INVOICE SCAN | Global Procurement Q2
Account Bill-To: DeepOptima Systems
Invoice Serial: #2026-0922-DF
====================================================
Sub-Item 1 (Enterprise SaaS): Legal Compliances IDE: Current Spend $95,000 | Optimized target $60,000 (due to idle seats)
Sub-Item 2 (Marketing): Dynamic PR Adwords: Current Spend $280,000 | Optimized target $160,000 (un-targeted bidding)
Sub-Item 3 (Travel Opex): Client Onboarding Flights: Current Spend $140,000 | Optimized target $80,000 (no early booking discounts)
Sub-Item 4 (R&D Labs): Specialized Graphics Server: Current Spend $150,000 | Optimized target $120,000 (waste cooling rates)
====================================================
OCR Metadata Validation checksum: 0x92AFCC"""

    val docxPreset = """DOCX BUSINESS MEMO: Internal Operational Expenditure Audit
Author: Quantitative Auditing Desk
Review Scope: Corporate Waste Outlay

1. TECHNICAL COMPLIANCE MATTERS
We noticed deep opex leakages. Specifically:
- Corporate Software licenses: current spend 250000, optimized spend 130000
- Multi-Region AWS clusters: current spend 390000, optimized spend 220000

2. TRAVEL & BRAND ACTIONS
- Executive Private travel: current spend 110000, optimized spend 50000
- Brand Agency Retainers: current spend 140000, optimized spend 100000

Recommendation: Deploy Attention Transformers to align opex limits."""

    val imagePreset = """[IMAGE SCAN RESPONSE] - OCR EXTRACT DIGITAL RECEIPT
Device: Terminal Model OCR-9
Date: 2026-05-23
---------------------------------------------
1. HQ Storefront Lease: current amount 520000, optimized amount 520000 (fixed rate)
2. Custom Dyes & Boxes: current amount 95000, optimized amount 65000 (un-negotiated local supplier)
3. Deadhead Courier Logistics: current amount 310000, optimized amount 210000 (redundant return trucks)
4. Legacy Email Relays: current amount 80000, optimized amount 45000 (duplicate mail mailers)
---------------------------------------------
Confidence Margin: 98.4%
Heurist Aligners: ACTIVE"""

    // Auto-fill textbox when format changes or screen starts
    LaunchedEffect(activeFormatIndex) {
        when (activeFormatIndex) {
            0 -> {
                manualInputText = spreadsheetPreset
                loadedFileName = "Global_Opex_Report.xlsx"
            }
            1 -> {
                manualInputText = pdfPreset
                loadedFileName = "Vendor_Licensing_Invoice.pdf"
            }
            2 -> {
                manualInputText = docxPreset
                loadedFileName = "Operational_Restructure_Plan.docx"
            }
            3 -> {
                manualInputText = imagePreset
                loadedFileName = "Receipt_Retail_Outbox_OCR.jpg"
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Core Header
        item {
            Text(
                text = "Data Core Integration Pipeline",
                style = MaterialTheme.typography.headlineSmall,
                color = CyberCobalt,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Replace sample corporate data by loading raw csv worksheets or scanning unstructured financial files (Spreadsheets, PDFs, Word docs, Images). Our optical extraction engine standardizes the inputs automatically.",
                style = MaterialTheme.typography.bodySmall,
                color = SoftGrayText
            )
        }

        // Operational credentials warning if Guest
        if (isGuest) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = WasteCoral.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = WasteCoral)
                        Column {
                            Text(
                                "Read-Only Compliance Mode Active",
                                style = MaterialTheme.typography.bodyMedium,
                                color = WasteCoral,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "You are currently logged in with a GUEST reviewer key. Role-Based Access Control has locked pipeline modifications. Change roles under the Help Hub tab to write changes.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // Active Validation Rules Checklist
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = IceBlueCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Interactive OCR Alignment Rules",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Active Schema Validated", fontSize = 11.sp, color = NeonEmerald)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SchemaPill(column = "Department", mapped = true)
                        SchemaPill(column = "CurrentSpend", mapped = true)
                        SchemaPill(column = "OptimizedSpend", mapped = true)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SchemaPill(column = "Category", mapped = true, optional = true)
                        SchemaPill(column = "LeakExplanation", mapped = true, optional = true)
                    }
                }
            }
        }

        // Format Selector Tabs
        item {
            Text(
                text = "Select Data Format or OCR Target Source",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                formatsList.forEachIndexed { i, fmt ->
                    val isSelected = activeFormatIndex == i
                    Box(
                        modifier = Modifier
                            .background(
                                if (isSelected) CyberCobalt.copy(alpha = 0.2f) else SolidGrayCard,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { activeFormatIndex = i }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = fmt.third,
                                contentDescription = null,
                                tint = if (isSelected) CyberCobalt else SoftGrayText,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = fmt.second,
                                fontSize = 11.sp,
                                color = if (isSelected) CyberCobalt else Color.White,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // Simulated File Pickers
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = IceBlueCard.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(
                            imageVector = formatsList[activeFormatIndex].third,
                            contentDescription = null,
                            tint = NeonEmerald,
                            modifier = Modifier.size(24.dp)
                        )
                        Column {
                            Text(
                                "Simulated Active Attachment",
                                style = MaterialTheme.typography.labelSmall,
                                color = SoftGrayText
                            )
                            Text(
                                text = loadedFileName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Simulated Device File Drag Drop Trigger
                    Button(
                        onClick = {
                            // Loop files
                            when (activeFormatIndex) {
                                0 -> {
                                    manualInputText = spreadsheetPreset
                                    loadedFileName = "Global_Opex_Report.xlsx"
                                }
                                1 -> {
                                    manualInputText = pdfPreset
                                    loadedFileName = "Vendor_Licensing_Invoice.pdf"
                                }
                                2 -> {
                                    manualInputText = docxPreset
                                    loadedFileName = "Operational_Restructure_Plan.docx"
                                }
                                3 -> {
                                    manualInputText = imagePreset
                                    loadedFileName = "Receipt_Retail_Outbox_OCR.jpg"
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SolidGrayCard),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.White)
                            Text("Simulate Pick File", fontSize = 10.sp, color = Color.White)
                        }
                    }
                }
            }
        }

        // Custom OCR & Data Edit Console
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Customize Unstructured Document Text",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Source Edit Console",
                        style = MaterialTheme.typography.bodySmall,
                        color = SoftGrayText
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = manualInputText,
                    onValueChange = { if (!isGuest) manualInputText = it },
                    label = { Text("OCR File Text Stream Preview") },
                    placeholder = { Text("Enter tabular or unformatted rows listing departments and outlay figures.") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .testTag("unstructured_data_text_field"),
                    readOnly = isGuest,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberCobalt,
                        unfocusedBorderColor = SolidGrayCard,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = CyberCobalt,
                        unfocusedLabelColor = SoftGrayText,
                        disabledBorderColor = SolidGrayCard.copy(alpha = 0.5f)
                    )
                )
            }
        }

        // Parse Action Button (with animated processing states)
        item {
            Button(
                onClick = {
                    if (!isGuest) {
                        viewModel.importDocumentData(
                            format = formatsList[activeFormatIndex].second,
                            fileName = loadedFileName,
                            content = manualInputText
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("import_file_format_button"),
                enabled = !isExtracting && !isGuest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberCobalt,
                    contentColor = Color.White,
                    disabledContainerColor = CyberCobalt.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isExtracting) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = extractionStatus ?: "De-serializing file matrices...", color = Color.White)
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Send, contentDescription = null, tint = Color.White)
                        Text("Extract & Core Sync Workspace", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Live Operational Reporting Outputs (Success / Error diagnostics)
        item {
            AnimatedContent(
                targetState = Triple(importError, importSuccess, report),
                transitionSpec = { fadeIn() togetherWith fadeOut() }
            ) { (err, scc, rep) ->
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (err != null) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WasteCoral.copy(alpha = 0.15f)),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = WasteCoral)
                                Column {
                                    Text("Extraction & Parsing Defect", style = MaterialTheme.typography.bodyMedium, color = WasteCoral, fontWeight = FontWeight.Bold)
                                    Text(err, style = MaterialTheme.typography.bodySmall, color = Color.White)
                                }
                            }
                        }
                    }

                    if (scc != null) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = NeonEmerald.copy(alpha = 0.15f)),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = NeonEmerald)
                                Column {
                                    Text("Pipeline Parsing Restructure Completed", style = MaterialTheme.typography.bodyMedium, color = NeonEmerald, fontWeight = FontWeight.Bold)
                                    Text(scc, style = MaterialTheme.typography.bodySmall, color = Color.White)
                                }
                            }
                        }
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = IceBlueCard.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "CURRENT SYNCED LEDGER SCHEMAS",
                                style = MaterialTheme.typography.labelSmall,
                                color = SoftGrayText,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Dataset Name: ${rep.reportName}", style = MaterialTheme.typography.bodySmall, color = Color.White)
                            Text("Active Model Array Records: ${rep.costs.size} items", style = MaterialTheme.typography.bodySmall, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SchemaPill(column: String, mapped: Boolean, optional: Boolean = false) {
    Box(
        modifier = Modifier
            .background(
                if (mapped) NeonEmerald.copy(alpha = 0.15f) else SolidGrayCard,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
                imageVector = if (mapped) Icons.Default.Check else Icons.Default.Refresh,
                contentDescription = null,
                tint = if (mapped) NeonEmerald else SoftGrayText,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = if (optional) "$column (opt)" else column,
                fontSize = 11.sp,
                color = if (mapped) NeonEmerald else SoftGrayText
            )
        }
    }
}
