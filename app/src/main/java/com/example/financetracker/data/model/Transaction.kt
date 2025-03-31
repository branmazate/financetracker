package com.example.financetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.financetracker.data.db.converters.DateConverter
import com.example.financetracker.data.db.converters.RecurrenceIntervalConverter
import com.example.financetracker.data.db.converters.TransactionTypeConverter
import java.util.Date

enum class TransactionType { INCOME, EXPENSE }
enum class RecurrenceInterval { DAILY, WEEKLY, MONTHLY, YEARLY }

@Entity(tableName = "transactions")
@TypeConverters(
    DateConverter::class,
    TransactionTypeConverter::class,
    RecurrenceIntervalConverter::class)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: TransactionType = TransactionType.EXPENSE, //Enum: Income or Expense
    val amount: Double = 0.0,
    val date: Date = Date(),
    val category: String = "",
    val accountId: Long = 0L, // Foreign key to Account entity
    val description: String? = null,
    val recurring: Boolean = false,
    val recurrenceInterval: RecurrenceInterval? = null, // For recurrent transactions
) {

}