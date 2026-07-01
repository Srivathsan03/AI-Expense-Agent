package com.sri.aiexpenseagent.agent.tool

import com.sri.aiexpenseagent.agent.model.ToolResult
import com.sri.aiexpenseagent.data.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchExpenseTool(
    private val repository: ExpenseRepository
) : AgentTool {
    override val name = "search_expense"
    override val description = "Search expenses by title or category"

    override suspend fun execute(arguments: Map<String, String>): ToolResult = withContext(
        Dispatchers.IO
    ) {
        val query = arguments["query"] ?: return@withContext ToolResult.Error("Query is required")
        val expenses = repository.searchExpenses(query)
        return@withContext ToolResult.Success("Expenses found", expenses)
    }
}