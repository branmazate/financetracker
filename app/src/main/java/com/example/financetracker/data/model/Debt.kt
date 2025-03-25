package com.example.financetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.financetracker.data.db.converters.DateConverter
import com.example.financetracker.data.db.converters.PaymentListConverter
import java.util.Date

//TODO separate Debt and Payment entities in different databases

@Entity(tableName = "debts")
@TypeConverters(DateConverter::class, PaymentListConverter::class)
data class Debt(
    @PrimaryKey (autoGenerate = true)
    val id: Long = 0,
    val type: DebtType,
    val creditor: String,
    val totalAmount: Double,
    val remainingAmount: Double,
    val interestRate: Double = 0.0,
    val startDate: Date,
    val endDate: Date,
    val dueDate: Date,
    val payments: List<Payment> = emptyList(),
    val status: DebtStatus = DebtStatus.ACTIVE
) {
    enum class DebtType {
        OWED,
        OWING
    }
    enum class DebtStatus {
        ACTIVE,
        PAID,
        OVERDUE
    }
    data class Payment(
        val date: Date,
        val amount: Double,
        val note: String? = null
    )
}
