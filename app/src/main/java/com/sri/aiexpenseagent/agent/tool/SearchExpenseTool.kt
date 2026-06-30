package com.sri.aiexpenseagent.agent.tool

import com.sri.aiexpenseagent.agent.model.ToolResult
import com.sri.aiexpenseagent.data.repository.ExpenseRepository

class SearchExpenseTool(
    private val repository: ExpenseRepository
): AgentTool {
    override val name = "search_expense"
    override val description = "Search expenses by title or category"

    override suspend fun execute(arguments: Map<String, String>): ToolResult {
        val query = arguments["query"] ?: return ToolResult.Error("Query is required")
        val expenses = repository.searchExpenses(query)
        return ToolResult.Success("Expenses found", expenses)
    }
}