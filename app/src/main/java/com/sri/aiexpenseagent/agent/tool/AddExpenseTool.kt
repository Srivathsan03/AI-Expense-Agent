package com.sri.aiexpenseagent.agent.tool

import com.sri.aiexpenseagent.agent.model.ToolResult
import com.sri.aiexpenseagent.data.local.ExpenseEntity
import com.sri.aiexpenseagent.data.repository.ExpenseRepository

class AddExpenseTool(
    private val repository: ExpenseRepository
) : AgentTool {
    override val name = "add_expense"
    override val description = "Add a new expense"

    override suspend fun execute(arguments: Map<String, String>): ToolResult {
        val title = arguments["title"] ?: return ToolResult.Error("Title is required")
        val amount = arguments["amount"]?.toDoubleOrNull()
            ?: return ToolResult.Error("Amount is required")
        val category = arguments["category"] ?: return ToolResult.Error("Category is required")

        repository.insertExpense(
            ExpenseEntity(
                title = title,
                amount = amount,
                category = category,
                createdAt = System.currentTimeMillis()
            )
        )

        return ToolResult.Success("Expense added successfully")
    }
}