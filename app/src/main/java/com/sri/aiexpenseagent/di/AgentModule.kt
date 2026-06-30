package com.sri.aiexpenseagent.di

import com.sri.aiexpenseagent.agent.ai.ExpenseAiService
import com.sri.aiexpenseagent.agent.ai.ExpenseAiServiceImpl
import com.sri.aiexpenseagent.agent.llm.GeminiLlmClient
import com.sri.aiexpenseagent.agent.llm.GeminiRepository
import com.sri.aiexpenseagent.agent.llm.LlmClient
import com.sri.aiexpenseagent.agent.llm.PromptBuilder
import com.sri.aiexpenseagent.agent.registry.ToolRegistry
import com.sri.aiexpenseagent.agent.tool.AddExpenseTool
import com.sri.aiexpenseagent.agent.tool.GetExpenseTool
import com.sri.aiexpenseagent.agent.tool.SearchExpenseTool
import com.sri.aiexpenseagent.data.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AgentModule {

    @Provides
    @Singleton
    fun provideGeminiRepository(): GeminiRepository {
        return GeminiRepository()
    }

    @Provides
    @Singleton
    fun providePromptBuilder(): PromptBuilder {
        return PromptBuilder()
    }

    @Provides
    @Singleton
    fun provideLlmClient(
        repository: GeminiRepository,
        promptBuilder: PromptBuilder
    ): LlmClient {
        return GeminiLlmClient(repository, promptBuilder)
    }

    @Provides
    @Singleton
    fun provideExpenseAiService(
        llmClient: LlmClient
    ): ExpenseAiService {
        return ExpenseAiServiceImpl(llmClient)
    }

    @Provides
    @Singleton
    fun provideToolRegistry(
        repository: ExpenseRepository
    ): ToolRegistry {
        return ToolRegistry(
            listOf(
                AddExpenseTool(repository),
                GetExpenseTool(repository),
                SearchExpenseTool(repository)
            )
        )
    }
}
