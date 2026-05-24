package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DemandForecastPoint
import com.example.data.DepartmentCost
import com.example.ui.theme.*

/**
 * Custom High-Fidelity Line Chart comparing LSTM, GRU, and Transformer Forecast pathways.
 */
@Composable
fun ComparativeForecastChart(
    points: List<DemandForecastPoint>,
    modifier: Modifier = Modifier
) {
    if (points.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("No forecasting data computed.", color = SoftGrayText)
        }
        return
    }

    val texMeasurer = rememberTextMeasurer()

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IceBlueCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Dynamic Predictive Demand Forecasting (LSTM vs GRU vs Transformer)",
                    style = MaterialTheme.typography.titleMedium,
                    color = PureWhite
                )
                Text(
                    text = "12 Months Outlook",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftGrayText
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Legends Indicator Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LegendIndicator(color = SoftGrayText, label = "Historical Demand", isDashed = true)
                LegendIndicator(color = CyberCobalt, label = "LSTM Model")
                LegendIndicator(color = FutureViolet, label = "GRU Model")
                LegendIndicator(color = NeonEmerald, label = "Transformer")
                LegendIndicator(color = CyberCobalt.copy(alpha = 0.15f), label = "Confidence Range", isBlock = true)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Canvas drawing
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Transparent)
            ) {
                val width = size.width
                val height = size.height

                val paddingLeft = 50.dp.toPx()
                val paddingRight = 10.dp.toPx()
                val paddingTop = 15.dp.toPx()
                val paddingBottom = 30.dp.toPx()

                val chartWidth = width - paddingLeft - paddingRight
                val chartHeight = height - paddingTop - paddingBottom

                // Math bounds
                val maxVal = points.flatMap { 
                    listOf(it.lstmForecast, it.gruForecast, it.transformerForecast, it.historicalDemand ?: 0.0, it.confidenceIntervalMax)
                }.maxOrNull() ?: 100.0
                val minVal = 0.0 // Baseline floor
                val valRange = maxVal - minVal

                val stepX = chartWidth / (points.size - 1)

                // 1. Draw grid horizontal lines & text metrics
                val gridCount = 4
                for (i in 0..gridCount) {
                    val ratio = i.toFloat() / gridCount
                    val y = height - paddingBottom - (ratio * chartHeight)
                    val valueRepr = minVal + (ratio * valRange)

                    // Draw line
                    drawLine(
                        color = SolidGrayCard.copy(alpha = 0.45f),
                        start = Offset(paddingLeft, y),
                        end = Offset(width - paddingRight, y),
                        strokeWidth = 1.dp.toPx()
                    )

                    // Draw label
                    drawText(
                        textMeasurer = texMeasurer,
                        text = "$${String.format("%.0f", valueRepr / 1000)}k",
                        style = TextStyle(color = SoftGrayText, fontSize = 9.sp),
                        topLeft = Offset(5.dp.toPx(), y - 7.dp.toPx())
                    )
                }

                // 2. Draw vertical period lines & X Labels
                points.forEachIndexed { idx, point ->
                    val x = paddingLeft + (idx * stepX)
                    
                    // Period title text format Month 1 -> M1
                    val labelText = point.period.replace("Month ", "M")

                    if (idx % 2 == 0 || idx == points.size - 1) {
                        drawText(
                            textMeasurer = texMeasurer,
                            text = labelText,
                            style = TextStyle(color = SoftGrayText, fontSize = 9.sp),
                            topLeft = Offset(x - 6.dp.toPx(), height - paddingBottom + 5.dp.toPx())
                        )
                    }
                }

                // Helper map values to coordinates
                fun getPointOffset(index: Int, rawValue: Double): Offset {
                    val x = paddingLeft + (index * stepX)
                    val y = height - paddingBottom - (((rawValue - minVal) / valRange).toFloat() * chartHeight)
                    return Offset(x, y)
                }

                // 3. Draw LSTM Confidence interval fill
                val pathInterval = Path()
                // Top boundary (Confidence Max)
                points.forEachIndexed { idx, pt ->
                    val offset = getPointOffset(idx, pt.confidenceIntervalMax)
                    if (idx == 0) pathInterval.moveTo(offset.x, offset.y) else pathInterval.lineTo(offset.x, offset.y)
                }
                // Bottom boundary (Confidence Min) backward
                for (idx in points.indices.reversed()) {
                    val pt = points[idx]
                    val offset = getPointOffset(idx, pt.confidenceIntervalMin)
                    pathInterval.lineTo(offset.x, offset.y)
                }
                pathInterval.close()

                drawPath(
                    path = pathInterval,
                    color = CyberCobalt.copy(alpha = 0.1f),
                    style = androidx.compose.ui.graphics.drawscope.Fill
                )

                // 4. Draw Lines for predictions
                val lstmPath = Path()
                val gruPath = Path()
                val transformerPath = Path()
                val historicalPath = Path()

                points.forEachIndexed { idx, pt ->
                    val lstmOffset = getPointOffset(idx, pt.lstmForecast)
                    val gruOffset = getPointOffset(idx, pt.gruForecast)
                    val transOffset = getPointOffset(idx, pt.transformerForecast)

                    if (idx == 0) {
                        lstmPath.moveTo(lstmOffset.x, lstmOffset.y)
                        gruPath.moveTo(gruOffset.x, gruOffset.y)
                        transformerPath.moveTo(transOffset.x, transOffset.y)
                    } else {
                        lstmPath.lineTo(lstmOffset.x, lstmOffset.y)
                        gruPath.lineTo(gruOffset.x, gruOffset.y)
                        transformerPath.lineTo(transOffset.x, transOffset.y)
                    }

                    // Historical points
                    pt.historicalDemand?.let { hist ->
                        val histOffset = getPointOffset(idx, hist)
                        if (idx == 0) {
                            historicalPath.moveTo(histOffset.x, histOffset.y)
                        } else {
                            historicalPath.lineTo(histOffset.x, histOffset.y)
                        }
                    }
                }

                // Draw Historical
                drawPath(
                    path = historicalPath,
                    color = SoftGrayText,
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                )

                // Draw LSTM Line
                drawPath(
                    path = lstmPath,
                    color = CyberCobalt,
                    style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw GRU Line
                drawPath(
                    path = gruPath,
                    color = FutureViolet,
                    style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw Transformer Line
                drawPath(
                    path = transformerPath,
                    color = NeonEmerald,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }
    }
}

/**
 * Custom Allocation bar tracking actual and optimized allocations, highlighting wastage
 */
@Composable
fun CapitalOptimizationBar(
    costs: List<DepartmentCost>,
    modifier: Modifier = Modifier
) {
    if (costs.isEmpty()) {
        return
    }

    val totalCurrent = costs.sumOf { it.currentSpend }
    val totalOptimized = costs.sumOf { it.optimizedSpend }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IceBlueCard),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Departmental Wastage Analysis",
                style = MaterialTheme.typography.titleMedium,
                color = PureWhite
            )
            Spacer(modifier = Modifier.height(14.dp))

            costs.forEach { cost ->
                val ratio = if (totalCurrent > 0.0) {
                    (cost.currentSpend / totalCurrent).toFloat().coerceIn(0f, 1f)
                } else {
                    0f
                }
                val optRatio = if (totalCurrent > 0.0) {
                    (cost.optimizedSpend / totalCurrent).toFloat().coerceIn(0f, 1f)
                } else {
                    0f
                }
                val savingSavings = cost.potentialSavings

                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        if (cost.isWasteful) WasteCoral else NeonEmerald,
                                        RoundedCornerShape(4.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = cost.department,
                                style = MaterialTheme.typography.bodyMedium,
                                color = PureWhite
                            )
                        }
                        Text(
                            text = "$${String.format("%,.0f", cost.currentSpend)} → $${String.format("%,.0f", cost.optimizedSpend)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (cost.isWasteful) WasteCoral else SoftGrayText
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Double Progress Bar (Current vs Optimized)
                    Box(
                        modifier = Modifier
                             .fillMaxWidth()
                            .height(10.dp)
                            .background(SolidGrayCard, RoundedCornerShape(5.dp))
                    ) {
                        // Current Allocation
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(ratio)
                                .fillMaxHeight()
                                .background(
                                    if (cost.isWasteful) WasteCoral.copy(alpha = 0.4f) else CyberCobalt.copy(alpha = 0.4f),
                                    RoundedCornerShape(5.dp)
                                )
                        )
                        // Optimized Target (sits on in-fill)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(optRatio)
                                .fillMaxHeight()
                                .background(
                                    if (cost.isWasteful) WasteCoral else NeonEmerald,
                                    RoundedCornerShape(5.dp)
                                )
                        )
                    }

                    if (savingSavings > 0.1) {
                        Text(
                            text = "Potential saving: $${String.format("%,.0f", savingSavings)} (Leak: ${cost.leakageExplanation})",
                            style = MaterialTheme.typography.labelSmall,
                            color = SoftGrayText,
                            modifier = Modifier.padding(top = 2.dp, start = 14.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LegendIndicator(
    color: Color,
    label: String,
    isDashed: Boolean = false,
    isBlock: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (isBlock) {
            Box(
                modifier = Modifier
                    .size(14.dp, 8.dp)
                    .background(color)
            )
        } else if (isDashed) {
            Canvas(modifier = Modifier.size(16.dp, 3.dp)) {
                drawLine(
                    color = color,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(14.dp, 3.dp)
                    .background(color, RoundedCornerShape(1.5.dp))
            )
        }
        Text(text = label, fontSize = 10.sp, color = SoftGrayText)
    }
}
