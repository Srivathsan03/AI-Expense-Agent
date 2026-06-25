package com.sri.aiexpenseagent.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sri.aiexpenseagent.ui.theme.AIExpenseAgentTheme

@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel = viewModel()) {
    ExpenseScreenContent()
}

@Composable
fun ExpenseScreenContent() {
    Scaffold() { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }
}

@Preview
@Composable
private fun Preview_ExpenseScreen() {
    AIExpenseAgentTheme() {
        ExpenseScreenContent()
    }
}