package com.example.financetracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.financetracker.data.db.converters.DateConverter
import com.example.financetracker.data.db.converters.PaymentListConverter
import com.example.financetracker.data.db.dao.AccountDao
import com.example.financetracker.data.db.dao.DebtDao
import com.example.financetracker.data.db.dao.TransactionDao
import com.example.financetracker.data.model.BankAccount
import com.example.financetracker.data.model.Debt
import com.example.financetracker.data.model.Transaction

@Database(
    entities = [Transaction::class, BankAccount::class, Debt::class],
    version = 1
)

@TypeConverters (DateConverter::class, PaymentListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun debtDao(): DebtDao

    companion object {
        //TODO singleton pattern
    }
}