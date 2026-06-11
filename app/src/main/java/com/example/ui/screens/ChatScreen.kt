package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.FinanceViewModel
import com.example.ui.theme.*

@Composable
fun ChatScreen(
    viewModel: FinanceViewModel,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val isChatLoading by viewModel.isChatLoading.collectAsStateWithLifecycle()
    var rawInputText by remember { mutableStateOf("") }

    val listState = rememberLazyListState()

    // Slide up text automatically when a message is added
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Chat Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "AI Financial Optimizer",
                    style = MaterialTheme.typography.titleMedium,
                    color = NeonEmerald
                )
                Text(
                    text = "Powered by Gemini 3.5 Flash Model",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftGrayText
                )
            }

            IconButton(onClick = { viewModel.clearChat() }) {
                Icon(Icons.Default.Delete, contentDescription = "Clear Session", tint = WasteCoral)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Chats messaging board
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { msg ->
                val isAi = msg.sender.contains("AI") || msg.sender.contains("System")
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isAi) Arrangement.Start else Arrangement.End
                ) {
                    Card(
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = if (isAi) 0.dp else 12.dp,
                            bottomEnd = if (isAi) 12.dp else 0.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isAi) IceBlueCard else SolidGrayCard
                        ),
                        modifier = Modifier.widthIn(max = 280.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = msg.sender,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isAi) NeonEmerald else CyberCobalt
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = msg.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = PureWhite
                            )
                        }
                    }
                }
            }

            if (isChatLoading) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = IceBlueCard),
                            modifier = Modifier.widthIn(max = 200.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = NeonEmerald,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "AI is computing audit...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SoftGrayText
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Chat Input box row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = rawInputText,
                onValueChange = { rawInputText = it },
                placeholder = { Text("Ask about LSTM vs GRU trends...") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_text_field"),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (rawInputText.isNotBlank()) {
                            viewModel.sendChatMessage(rawInputText)
                            rawInputText = ""
                        }
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonEmerald,
                    unfocusedBorderColor = SolidGrayCard,
                    focusedTextColor = PureWhite,
                    unfocusedTextColor = PureWhite
                )
            )

            FloatingActionButton(
                onClick = {
                    if (rawInputText.isNotBlank()) {
                        viewModel.sendChatMessage(rawInputText)
                        rawInputText = ""
                    }
                },
                modifier = Modifier.testTag("chat_send_fab"),
                containerColor = NeonEmerald,
                contentColor = androidx.compose.ui.graphics.Color.White
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send Message", tint = androidx.compose.ui.graphics.Color.White)
            }
        }
    }
}
