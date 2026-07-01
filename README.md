# 🤖 AI Expense Agent

An AI-powered Android expense tracking application that allows users to manage expenses using natural language.

Instead of navigating multiple screens or filling forms, users can simply type commands like:

> "Spent ₹250 on groceries"

or

> "Show my pet expenses"

The application uses Google's Gemini LLM to understand user intent, determine the appropriate action, and execute the corresponding tool using a custom AI agent architecture.

---

## ✨ Features

- 🤖 AI-powered expense management
- 💬 Natural language expense entry
- 🔍 Search expenses using plain English
- 📋 View all expenses
- 🧠 Custom AI Agent implementation
- 🛠 Tool Calling Architecture
- 💾 Local storage using Room Database
- 🎨 Modern UI built with Jetpack Compose
- 🧩 Dependency Injection with Hilt
- 🏗 Clean Architecture + MVVM

---

## Example Commands

```
Bought tea for ₹20

Show my expenses

Search grocery

Search pet expenses
```

---

## Architecture

```
User
   │
   ▼
Chat UI
   │
   ▼
ExpenseViewModel
   │
   ▼
ExpenseAgent
   │
   ▼
ExpenseAIService
   │
   ▼
GeminiLlmClient
   │
   ▼
Gemini API
   │
   ▼
Tool Request
   │
   ▼
Tool Registry
   │
   ▼
AddExpenseTool
GetExpensesTool
SearchExpenseTool
   │
   ▼
ExpenseRepository
   │
   ▼
Room Database
```

---

## Tech Stack

### Android

- Kotlin
- Jetpack Compose
- MVVM
- Clean Architecture
- Hilt
- Room Database
- Kotlin Coroutines
- StateFlow

### AI

- Google Gemini API
- Prompt Engineering
- AI Agent Design
- Tool Calling Architecture
- JSON Response Parsing

---

## Project Structure

```
app/
│
├── agent/
│   ├── ai/
│   ├── llm/
│   ├── registry/
│   ├── tool/
│   └── model/
│
├── data/
│   ├── local/
│   └── repository/
│
├── ui/
│   ├── screen/
│   └── viewmodel/
│
└── di/
```

---

## AI Agent Workflow

```
User Message
      │
      ▼
Prompt Builder
      │
      ▼
Gemini LLM
      │
      ▼
Tool Request
      │
      ▼
Tool Registry
      │
      ▼
Execute Tool
      │
      ▼
Return Result
```

---

## Available Tools

| Tool | Description |
|------|-------------|
| Add Expense | Saves a new expense |
| Get Expenses | Retrieves all saved expenses |
| Search Expense | Searches expenses by title or category |

---

## Learning Objectives

This project was built to explore:

- AI-powered Android application development
- LLM integration in mobile apps
- Prompt engineering
- Tool calling architecture
- AI agent design
- Clean Architecture for AI applications
- Modern Android development best practices

---

## Future Improvements

- Native Gemini Function Calling
- Model Context Protocol (MCP) integration
- Voice input
- Receipt OCR
- Monthly AI-generated expense summaries
- Expense analytics and charts
- Cloud synchronization
- Multi-device support

---

## Getting Started

1. Clone the repository.

```
git clone <repository-url>
```

2. Add your Gemini API key.

```
local.properties

GEMINI_API_KEY=YOUR_API_KEY
```

3. Build and run the application.
