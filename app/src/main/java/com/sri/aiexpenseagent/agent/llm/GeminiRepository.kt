package com.sri.aiexpenseagent.agent.llm

import com.google.genai.Client
import com.google.genai.errors.ClientException
import com.google.genai.types.GenerateContentConfig
import com.sri.aiexpenseagent.BuildConfig
import com.sri.androidmentorchat.core.model.AIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class GeminiRepository {

    private val client by lazy {
        Client.Builder()
            .apiKey(BuildConfig.GEMINI_API_KEY)
            .build()
    }

    suspend fun geminiResponse(
        prompt: String
    ): String = withContext(Dispatchers.IO) {
        var attempt = 0
        val maxAttempts = 3
        var currentDelay = 2000L

        while (attempt < maxAttempts) {
            try {
                val response = client.models.generateContent(
                    AIModel.GEMINI_3_1_FLASH_LITE.modelId,
                    prompt,
                    GenerateContentConfig.builder().build()
                )
                return@withContext response.text() ?: "Empty response"
            } catch (e: ClientException) {
                val msg = e.message() ?: ""
                if (e.code() == 429) {
                    if (attempt < maxAttempts - 1) {
                        attempt++
                        delay(currentDelay.milliseconds)
                        currentDelay *= 2
                        continue
                    }
                    return@withContext if (msg.contains("retry in")) {
                        "Quota exceeded. Retry in ${msg.substringAfter("retry in").trim()}."
                    } else {
                        "Quota exceeded. Please try again later."
                    }
                }
                return@withContext "Client error (${e.code()}): $msg"
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext "Error: ${e.message}"
            }
        }
        return@withContext "Failed after $maxAttempts attempts"
    }
}