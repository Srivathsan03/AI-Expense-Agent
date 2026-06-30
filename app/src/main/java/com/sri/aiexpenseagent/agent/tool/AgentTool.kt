package com.sri.aiexpenseagent.agent.tool

import com.sri.aiexpenseagent.agent.model.ToolResult

interface AgentTool {

    val name: String
    val description: String
    suspend fun execute(
        arguments: Map<String, String>
    ): ToolResult
}