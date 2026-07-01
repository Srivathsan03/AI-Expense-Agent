package com.sri.aiexpenseagent.agent.model

import com.sri.aiexpenseagent.data.local.ExpenseEntity
import kotlinx.coroutines.flow.Flow

sealed interface ToolResult {

    data class Success(
        val message: String,
        val data: List<ExpenseEntity>? = null
    ) : ToolResult

    data class Error(
        val errorMessage: String
    ) : ToolResult
}