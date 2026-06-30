package com.sri.aiexpenseagent.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sri.aiexpenseagent.agent.ExpenseAgent
import com.sri.aiexpenseagent.data.local.ExpenseEntity
import com.sri.aiexpenseagent.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val agent: ExpenseAgent
) : ViewModel() {

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages = _chatMessages.asStateFlow()

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses = _expenses.asStateFlow()

    private val _searchResults = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    init {
        viewModelScope.launch {
            expenseRepository
                .getAllExpense()
                .collect {
                    _expenses.value = it
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
                    _searchResults.value = it
                }
        }
    }

//    fun sendMessage(prompt: String) {
//        Log.d(TAG, "sendMessage: prompt = $prompt")
//        _chatMessages.value += ChatMessage(prompt, isUser = true)
//        viewModelScope.launch(Dispatchers.IO) {
//            val prompt = _chatMessages.value.joinToString("\n") { history ->
//                "${if (history.isUser) "User" else "Gemini"}: ${history.text}"
//            }
//
//            try {
//                val response = client.models.generateContent(
//                    AIModel.GEMINI_3_1_FLASH_LITE.modelId,
//                    prompt,
//                    GenerateContentConfig.builder().build()
//                )
//                Log.d(TAG, "sendMessage: response = $response")
//                _chatMessages.value += ChatMessage(response.text().toString(), isUser = false)
//            } catch (e: ClientException) {
//                e.printStackTrace()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

    fun sendMessage(prompt: String) {
        _chatMessages.value += ChatMessage(text = prompt, isUser = true)
        viewModelScope.launch {
            agent.chat(prompt)
        }
    }
}