package com.sri.aiexpenseagent.agent.llm

import com.sri.aiexpenseagent.agent.model.ExpenseCategory

class PromptBuilder {

    private val role = "You are an AI Expense Agent."
    private val objective = """
            Your job is to determine which tool should be executed based on the user's request.
            Do not answer the user directly.
            Only decide the correct tool and its arguments.
        """.trimIndent()
    private val tools = """
            Available tools:
            
            1. add_expense
            Arguments:
            - title
            - amount
            - category
            
            2. get_expenses
            Arguments:
            None
            
            3. search_expense
            Arguments:
            - query
        """.trimIndent()
    private val rules = """
            Rules:
            Return ONLY valid JSON.
            Do not use Markdown.
            Do not explain anything.
            Never return natural language.
            If you don't know which tool to use, return:

            {
              "tool":"unknown",
              "arguments":{}
            }
        """.trimIndent()
    private val examples = """
            Examples:
            
            User:
            Spent ₹20 on tea
            
            Response:
            {
              "tool":"add_expense",
              "arguments":{
                  "title":"Tea",
                  "amount":"20",
                  "category":"FOOD"
              }
            }
            
            User:
            Show all expenses
            
            Response:
            {
              "tool":"get_expenses",
              "arguments":{}
            }
            
            User:
            Search tea expenses
            
            Response:
            {
              "tool":"search_expense",
              "arguments":{
                  "query":"Tea"
              }
            }
        """.trimIndent()

    private fun categories(): String {
        val categories = ExpenseCategory.entries.joinToString(separator = "\n") {
            "- ${it.name}"
        }
        return categories
    }

    fun buildPrompt(
        userMessage: String
    ): String {
        return buildString {
            appendLine(role)
            appendLine(objective)
            appendLine(tools)
            appendLine(rules)
            appendLine(examples)

            appendLine("Available expense categories:")
            appendLine(categories())
            appendLine()
            appendLine("Always choose exactly one category from the above list.")

            appendLine("User:")
            appendLine(userMessage)
        }
    }
}