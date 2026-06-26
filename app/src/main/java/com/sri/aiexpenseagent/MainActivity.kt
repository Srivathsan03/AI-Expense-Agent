package com.sri.aiexpenseagent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sri.aiexpenseagent.ui.screen.ExpenseScreen
import com.sri.aiexpenseagent.ui.theme.AIExpenseAgentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIExpenseAgentTheme {
                ExpenseScreen()
            }
        }
    }
}