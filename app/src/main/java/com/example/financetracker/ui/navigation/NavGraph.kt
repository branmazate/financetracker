package com.example.financetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financetracker.ui.screens.AccountFormScreen
import com.example.financetracker.ui.screens.TransactionFormScreen
import com.example.financetracker.ui.screens.dashboard.DashboardScreen
import com.example.financetracker.ui.viewmodels.AccountViewModel
import com.example.financetracker.ui.viewmodels.DashboardViewModel
import com.example.financetracker.ui.viewmodels.TransactionViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {val viewModel: DashboardViewModel = hiltViewModel();
            DashboardScreen(navController, viewModel)}
        composable("transactions") {val viewModel: TransactionViewModel = hiltViewModel();
            TransactionFormScreen(navController, viewModel)}
        composable("accounts") {val viewModel: AccountViewModel = hiltViewModel();
            AccountFormScreen(navController, viewModel) }
    }
}
