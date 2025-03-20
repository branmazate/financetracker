package com.example.financetracker.utils

import com.example.financetracker.data.model.Transaction
import com.example.financetracker.data.model.Debt

object ValidationHelper {
    fun isValidTransaction(transaction: Transaction): Boolean {
        return transaction.amount > 0 &&
                transaction.category.isNotBlank() &&
                transaction.date.time <= System.currentTimeMillis()
    }

    fun isValidDebtPayment(debt: Debt, amount: Double): Boolean {
        return amount > 0 && amount <= debt.remainingAmount
    }
}