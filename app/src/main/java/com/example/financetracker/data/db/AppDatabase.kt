package com.example.financetracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
    version = 1,
    exportSchema = true
)

@TypeConverters (DateConverter::class, PaymentListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun debtDao(): DebtDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this) {

                /*val migration1 = object : Migration(1,2){
                    override fun migrate(db: SupportSQLiteDatabase){
                        db.execSQL("ALTER TABLE transactions ADD COLUMN notes TEXT")
                    }
                }*/
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase:: class.java,
                    "finance_database"
                )
                    //.addMigrations(migration1)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
                INSTANCE = instance
                instance
            }
        }
    }
}