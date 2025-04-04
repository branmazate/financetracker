package com.example.financetracker.ui.screens.dashboard

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.financetracker.ui.viewmodels.DashboardViewModel
import com.github.mikephil.charting.charts.PieChart

@Composable
fun DashboardScreen (navController: NavController, viewModel:DashboardViewModel){
    val expenses by viewModel.expensesByCategory.collectAsState(emptyList())
    val balance by viewModel.currentBalance.collectAsState(0.0)

    Column {
        Text("Current Balance: $ $balance", style = MaterialTheme.typography.titleSmall)
        PieChart(expenses as Context?)
    }
}