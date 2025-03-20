package com.example.financetracker.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithTransactions(
    @Embedded val account: BankAccount,
    @Relation(
        parentColumn = "id",
        entityColumn = "accountId"
    )
    val transactions: List<Transaction>
)