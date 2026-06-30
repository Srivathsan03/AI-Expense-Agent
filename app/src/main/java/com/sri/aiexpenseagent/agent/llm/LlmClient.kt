package com.sri.aiexpenseagent.agent.llm

import com.sri.aiexpenseagent.agent.model.ToolRequest

interface LlmClient {

    suspend fun getToolCall(
        userMessage: String
    ): ToolRequest
}