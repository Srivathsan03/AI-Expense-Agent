package com.sri.aiexpenseagent.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sri.aiexpenseagent.agent.ExpenseAgent
import com.sri.aiexpenseagent.agent.model.ToolResult
import com.sri.aiexpenseagent.data.local.ExpenseEntity
import com.sri.aiexpenseagent.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val agent: ExpenseAgent
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            expenseRepository
                .getAllExpense()
                .collect {
                    _uiState.value = _uiState.value.copy(
                        expenses = it
                    )
                }
        }
    }

    fun addExpense(
        title: String,
        amount: Double,
        category: String
    ) {
        viewModelScope.launch {
            expenseRepository.insertExpense(
                ExpenseEntity(
                    title = title,
                    amount = amount,
                    category = category,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun searchExpenses(
        query: String
    ) {
        viewModelScope.launch {
            expenseRepository.searchExpenses(query)
                .collect {
                    _uiState.value = _uiState.value.copy(
                        searchResults = it
                    )
                }
        }
    }

    fun sendMessage(prompt: String) {
        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + ChatMessage(text = prompt, isUser = true)
        )
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = agent.chat(prompt)
            when(result) {
                is ToolResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            response = result.message
                        )
                    }
                }
                is ToolResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            response = result.errorMessage
                        )
                    }
                }
            }
        }
    }
}