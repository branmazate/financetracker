package com.example.financetracker.androidTest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.financetracker.data.db.AppDatabase
import com.example.financetracker.data.db.dao.AccountDao
import com.example.financetracker.data.db.dao.TransactionDao
import com.example.financetracker.data.model.BankAccount
import com.example.financetracker.data.model.Transaction
import com.example.financetracker.data.model.TransactionType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var transactionDao: TransactionDao
    private lateinit var accountDao: AccountDao

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        transactionDao = db.transactionDao()
        accountDao = db.accountDao()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadTransaction() = runBlocking {
        val account = BankAccount(
            name = "Test",
            type = BankAccount.AccountType.CHECKING,
            balance = 50.0,
        )
        val accountId = accountDao.insert(account)

        val transaction = Transaction(
            type = TransactionType.EXPENSE,
            amount = 50.0,
            date = Date(),
            categoryId = 1,
            accountId = accountId,
        )
        transactionDao.insert(transaction)
        val saved = transactionDao.getTransactionsByAccountId(accountId).first()
        assertEquals(1, saved.size)
        assertEquals(50.0,saved[0].amount,0.001)
    }

    @After
    fun cleanup() {
        db.close()
    }
}