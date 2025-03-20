package com.example.financetracker.data.model

import java.util.Date

data class DebtWithPayments (
    val debt: Debt,
    val totalPaid: Double,
    val nextDueDate: Date?
)