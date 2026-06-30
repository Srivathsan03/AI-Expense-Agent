package com.sri.aiexpenseagent.ui.screen

import com.sri.aiexpenseagent.data.local.ExpenseEntity

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val expenses: List<ExpenseEntity> = emptyList(),
    val searchResults: List<ExpenseEntity> = emptyList(),
    val isLoading: Boolean = false,
    val response: String = ""
)