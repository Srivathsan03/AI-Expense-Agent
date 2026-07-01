package com.sri.aiexpenseagent.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sri.aiexpenseagent.agent.ExpenseAgent
import com.sri.aiexpenseagent.agent.model.ToolResult
import com.sri.aiexpenseagent.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val agent: ExpenseAgent
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    fun sendMessage(prompt: String) {
        _uiState.update { state ->
            state.copy(
                messages = state.messages + ChatMessage(text = prompt, isUser = true)
            )
        }
        viewModelScope.launch {
            try {
                _uiState.update { state ->
                    state.copy(
                        isLoading = true
                    )
                }
                val result = agent.chat(prompt)
                when (result) {
                    is ToolResult.Success -> {
                        result.data?.let { expensesEntities ->
                            Log.d("TAG", "sendMessage: $expensesEntities")
                            val response = StringBuilder()
                            expensesEntities.forEach { expenseEntity ->
                                response.append(
                                    buildString {
                                        appendLine("Category: ${expenseEntity.category}")
                                        appendLine("Title: ${expenseEntity.title}")
                                        appendLine("Amount: ${expenseEntity.amount}")
                                        appendLine("CreatedAt: ${Date(expenseEntity.createdAt)}")
                                        appendLine()
                                    }
                                )
                            }
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    messages = state.messages + ChatMessage(
                                        text = response.toString(),
                                        isUser = false
                                    )
                                )
                            }
                        } ?: run {
                            Log.d("TAG", "sendMessage: data == null")
                            _uiState.update { state ->
                                Log.d("TAG", "sendMessage: data == null")
                                state.copy(
                                    isLoading = false,
                                    messages = state.messages + ChatMessage(
                                        text = result.message,
                                        isUser = false
                                    )
                                )
                            }
                        }
                    }

                    is ToolResult.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                messages = state.messages + ChatMessage(
                                    text = result.errorMessage,
                                    isUser = false
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}