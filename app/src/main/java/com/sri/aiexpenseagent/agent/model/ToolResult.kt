package com.sri.aiexpenseagent.agent.model

sealed interface ToolResult {

    data class Success(
        val message: String,
        val data: Any? = null
    ) : ToolResult

    data class Error(
        val errorMessage: String
    ) : ToolResult
}