package com.sri.aiexpenseagent.agent.tool

import com.sri.aiexpenseagent.agent.model.ToolResult
import com.sri.aiexpenseagent.data.repository.ExpenseRepository

class GetExpenseTool(
    private val repository: ExpenseRepository
): AgentTool {
    override val name = "get_expenses"
    override val description = "Get all saved expenses"

    override suspend fun execute(arguments: Map<String, String>): ToolResult {
        val expenses = repository.getAllExpense()
        return ToolResult.Success("Expenses found", expenses)
    }
}