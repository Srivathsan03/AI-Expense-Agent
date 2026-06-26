package com.sri.aiexpenseagent.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sri.aiexpenseagent.R
import com.sri.aiexpenseagent.ui.theme.AIExpenseAgentTheme
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel = hiltViewModel()) {
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    ExpenseScreenContent(
        chatMessages = chatMessages,
        onSendMessage = viewModel::sendMessage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreenContent(
    chatMessages: List<ChatMessage>,
    onSendMessage: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column() {
                        Text(text = stringResource(R.string.app_name))
                        Text(
                            text = "Track Expense with AI",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            val chatMessages = chatMessages
            val listState = rememberLazyListState()

            LaunchedEffect(chatMessages.size) {
                if (chatMessages.isNotEmpty()) {
                    listState.animateScrollToItem(chatMessages.lastIndex)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                state = listState
            ) {
                items(chatMessages.size) { index ->
                    val chatMessage = chatMessages[index]
                    val isUser = chatMessage.isUser

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                    ) {
                        MarkdownText(
                            modifier = Modifier
                                .background(
                                    color = if (isUser) MaterialTheme.colorScheme.surfaceVariant
                                    else MaterialTheme.colorScheme.secondaryContainer,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(12.dp),
                            markdown = chatMessage.text,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (isUser) MaterialTheme.colorScheme.onSurfaceVariant
                                else MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val promptState = rememberTextFieldState()
                TextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    state = promptState
                )
                IconButton(
                    onClick = {
                        onSendMessage(promptState.text.toString())
                        promptState.clearText()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                            .padding(8.dp),
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview_ExpenseScreen() {
    AIExpenseAgentTheme() {
        ExpenseScreenContent(
            chatMessages = listOf(
                ChatMessage("Hi", true),
                ChatMessage("Hello, how can I help you?", false)
            ),
            onSendMessage = {}
        )
    }
}