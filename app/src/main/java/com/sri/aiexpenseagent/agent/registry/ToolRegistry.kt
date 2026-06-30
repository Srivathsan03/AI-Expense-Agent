package com.sri.aiexpenseagent.agent.registry

import com.sri.aiexpenseagent.agent.tool.AgentTool

class ToolRegistry(
    tools: List<AgentTool>
) {

    private val registry = tools.associateBy { it.name }

    fun getTool(name: String): AgentTool? {
        return registry[name]
    }
}