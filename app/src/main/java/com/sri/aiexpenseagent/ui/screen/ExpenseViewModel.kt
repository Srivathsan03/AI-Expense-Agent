package com.sri.aiexpenseagent.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sri.aiexpenseagent.data.local.ExpenseEntity
import com.sri.aiexpenseagent.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

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
}