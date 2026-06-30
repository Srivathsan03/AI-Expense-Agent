package com.sri.aiexpenseagent.agent.ai

import com.sri.aiexpenseagent.agent.llm.LlmClient
import com.sri.aiexpenseagent.agent.model.ToolRequest

class ExpenseAiServiceImpl(
    private val llmClient: LlmClient
): ExpenseAiService {
    override suspend fun processMessage(userMessage: String): ToolRequest {
        return llmClient.getToolCall(userMessage)
    }
}