package com.sri.aiexpenseagent.agent.llm

import com.sri.aiexpenseagent.agent.model.ToolRequest
import kotlinx.serialization.json.Json

class GeminiLlmClient(
    private val repository: GeminiRepository,
    private val promptBuilder: PromptBuilder
) : LlmClient {
    override suspend fun getToolRequest(userMessage: String): ToolRequest {
        return try {
            val prompt = promptBuilder.buildPrompt(userMessage)
            val response = repository.geminiResponse(
                prompt = prompt
            )
            parseResponse(response)
        } catch (e: Exception) {
            ToolRequest(
                tool = "unknown",
                arguments = mapOf("error" to e.message.toString())
            )
        }
    }

    private fun parseResponse(
        json: String
    ): ToolRequest {
        return try {
            Json.decodeFromString(json)
        } catch (e: Exception) {
            ToolRequest(
                tool = "unknown",
                arguments = mapOf("error" to e.message.toString())
            )
        }
    }
}