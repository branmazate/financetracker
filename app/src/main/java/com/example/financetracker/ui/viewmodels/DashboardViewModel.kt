package com.example.financetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.financetracker.data.db.dao.CategoryExpense
import com.example.financetracker.data.repository.AccountRepository
import com.example.financetracker.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val transactionRepo: TransactionRepository,
    private val accountRepo: AccountRepository
) : ViewModel() {

    val expensesByCategory: Flow<List<CategoryExpense>> =
        transactionRepo.getExpensesTotalGroupedByCategory()

    val currentBalance: Double = accountRepo.getTotalBalanceFromCashAndCheckingAccounts()
}