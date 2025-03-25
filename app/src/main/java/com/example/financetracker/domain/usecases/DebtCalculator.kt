package com.example.financetracker.domain.usecases

import com.example.financetracker.data.repository.AccountRepository
import com.example.financetracker.data.repository.DebtRepository
import com.example.financetracker.data.repository.TransactionRepository
import javax.inject.Inject

class DebtCalculator @Inject constructor(
    private val debtRepo: DebtRepository,
    private val accountRepo: AccountRepository,
    private val transactionRepo: TransactionRepository
) {
    suspend fun calculateDebtToIncomeRatio(): Double {
        val totalDebt: Double = debtRepo.getTotalActiveDebts()
        val totalIncome: Double = transactionRepo.getMonthlyIncome()

        return if (totalIncome < 0) totalDebt/totalIncome
        else throw IllegalStateException("No income registered")
    }

    //TODO Logic to optimize payments
}