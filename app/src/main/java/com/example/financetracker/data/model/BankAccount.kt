package com.example.financetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_accounts")
data class BankAccount(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: AccountType,
    var balance: Double,
    val currency: String,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    enum class AccountType {
        CHECKING,
        SAVINGS,
        CREDIT_CARD,
        CASH,
        INVESTMENT
    }

}