package com.sri.aiexpenseagent.agent

import com.sri.aiexpenseagent.agent.ai.ExpenseAiService
import com.sri.aiexpenseagent.agent.model.ToolResult
import com.sri.aiexpenseagent.agent.registry.ToolRegistry

import javax.inject.Inject

class ExpenseAgent @Inject constructor(
    private val aiService: ExpenseAiService,
    private val registry: ToolRegistry
) {
    suspend fun chat(message: String): ToolResult {
        val toolCall = aiService.processMessage(message)
        val tool = registry.getTool(toolCall.tool)
            ?: return ToolResult.Error("Tool not found")
        return tool.execute(toolCall.arguments)
    }
}