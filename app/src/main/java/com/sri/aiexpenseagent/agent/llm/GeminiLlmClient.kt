package com.sri.aiexpenseagent.agent.llm

import kotlinx.serialization.json.Json
import com.sri.aiexpenseagent.agent.model.ToolRequest

class GeminiLlmClient(
    private val repository: GeminiRepository,
    private val promptBuilder: PromptBuilder
) : LlmClient {
    override suspend fun getToolCall(userMessage: String): ToolRequest {
        val prompt = promptBuilder.buildPrompt(userMessage)
        val response = repository.geminiResponse(
            prompt = prompt
        )
        return parseResponse(response)
    }

    private fun parseResponse(
        json: String
    ): ToolRequest {
        return Json.decodeFromString(json)
    }
}