package com.sri.aiexpenseagent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.sri.aiexpenseagent.data.local.ExpenseDatabase
import com.sri.aiexpenseagent.data.local.ExpenseEntity
import com.sri.aiexpenseagent.ui.screen.ExpenseScreen
import com.sri.aiexpenseagent.ui.theme.AIExpenseAgentTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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