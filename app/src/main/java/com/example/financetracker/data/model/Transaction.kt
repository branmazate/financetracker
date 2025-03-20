package com.example.financetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date
import com.example.financetracker.data.db.converters.DateConverter

@Entity(tableName = "transactions")
@TypeConverters(DateConverter::class)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: TransactionType, //Enum: Income or Expense
    val amount: Double,
    val date: Date,
    val category: String,
    val accountId: Long, // Foreign key to Account entity
    val description: String? = null,
    val recurring: Boolean = false,
    val recurrenceInterval: RecurrenceInterval? = null // For recurrent transactions
) {
    enum class TransactionType { INCOME, EXPENSE }
    enum class RecurrenceInterval { DAILY, WEEKLY, MONTHLY, YEARLY }
}