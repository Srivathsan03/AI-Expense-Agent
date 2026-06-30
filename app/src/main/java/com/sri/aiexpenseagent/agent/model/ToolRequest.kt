package com.sri.aiexpenseagent.agent.model

import kotlinx.serialization.Serializable

@Serializable
data class ToolRequest(
    val tool: String,
    val arguments: Map<String, String>
)