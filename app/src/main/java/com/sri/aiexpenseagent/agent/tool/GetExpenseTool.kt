package com.sri.aiexpenseagent.agent.tool

import com.sri.aiexpenseagent.agent.model.ToolResult
import com.sri.aiexpenseagent.data.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetExpenseTool(
    private val repository: ExpenseRepository
) : AgentTool {
    override val name = "get_expenses"
    override val description = "Get all saved expenses"

    override suspend fun execute(arguments: Map<String, String>): ToolResult = withContext(
        Dispatchers.IO
    ) {
        val expenses = repository.getAllExpense()
        return@withContext ToolResult.Success("Expenses found", expenses)
    }
}