package com.example.financetracker.utils

import com.example.financetracker.data.model.Debt
import com.example.financetracker.data.model.Transaction

object ValidationHelper {
    fun isValidTransaction(transaction: Transaction) {
        require(transaction.amount >= 0) { "Transaction amount cannot be negative" }
    }

    fun isValidDebtPayment(debt: Debt, amount: Double): Boolean {
        return amount > 0 && amount <= debt.remainingAmount
    }

    //Function to validate that a debt is valid (amount > 0, interest rate > 0)
    fun isValidDebt(debt:Debt, interestRate: Double ):Boolean{
        return debt.totalAmount > 0 && interestRate > 0
    }
}