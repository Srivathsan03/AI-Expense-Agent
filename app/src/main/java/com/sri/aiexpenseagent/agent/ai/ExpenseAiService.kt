package com.sri.aiexpenseagent.agent.ai

import com.sri.aiexpenseagent.agent.model.ToolRequest

interface ExpenseAiService {

    suspend fun processMessage(
        userMessage: String
    ): ToolRequest
}