package com.sri.aiexpenseagent.data.repository

import com.sri.aiexpenseagent.data.local.ExpenseDao
import com.sri.aiexpenseagent.data.local.ExpenseEntity

import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    fun getAllExpense() = expenseDao.getAllExpenses()

    suspend fun insertExpense(expenseEntity: ExpenseEntity) {
        expenseDao.insertExpense(expenseEntity)
    }

    fun searchExpenses(query: String) = expenseDao.searchExpenses(query)

    suspend fun deleteExpense(expenseEntity: ExpenseEntity) {
        expenseDao.deleteEntity(expenseEntity)
    }

    suspend fun deleteAllExpenses() {
        expenseDao.deleteAll()
    }
}