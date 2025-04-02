package com.example.financetracker.data.repository

import com.example.financetracker.data.db.dao.AccountDao
import com.example.financetracker.data.db.dao.CategoryExpense
import com.example.financetracker.data.db.dao.TransactionDao
import com.example.financetracker.data.model.Transaction
import com.example.financetracker.data.model.TransactionType
import com.example.financetracker.utils.ValidationHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val ioDispatcher: CoroutineContext
) : TransactionRepository {
    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    override suspend fun addTransaction(transaction: Transaction) = withContext(ioDispatcher)
    {
        ValidationHelper.isValidTransaction(transaction)
        val transactionId = transactionDao.insert(transaction)
        val amount = when (transaction.type) {
            TransactionType.INCOME -> transaction.amount
            TransactionType.EXPENSE -> transaction.amount
        }

        if (transaction.recurring){
            scheduleRecurringTransaction(transaction)
        }
    }

    override fun getMonthlyExpenseReport(): Flow<List<CategoryExpense>> {
        val today = LocalDate.now()
        val startOfMonth = today
            .withDayOfMonth(1)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val endOfMonth = today
            .withDayOfMonth(today.lengthOfMonth())
            .atTime(23, 59, 59, 999999999)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        return transactionDao.getExpenseSummaryByCategory(startOfMonth, endOfMonth)
    }

    private fun scheduleRecurringTransaction(original:Transaction){
        //TODO Logic to schedule recurring transactions
    }

    override suspend fun getRecurringTransactions(): Flow<List<Transaction>> {
        return transactionDao.getRecurringTransactions()
    }
    override suspend fun updateTransaction(transaction: Transaction) {
        val original = transactionDao.getTransactionById(transaction.id)
            ?: throw Exception("Transaction not found")
        val balanceDifference = transaction.amount - original.amount
        accountDao.updateBalance(transaction.accountId, balanceDifference)

        transactionDao.update(transaction)
    }

    override suspend fun getMonthlyIncome(): Double {
        return transactionDao.getMonthlyIncome()
    }

    override suspend fun getExpenseSummary(start: Long, end: Long): Flow<Map<String, Double>> {
        return transactionDao.getExpenseSummaryByCategory(start, end)
            .map { list: List<CategoryExpense> ->
                list.associate { expense: CategoryExpense ->
                    expense.categoryId.toString() to expense.total
                }
            }
    }
}

interface TransactionRepository {
    fun getAllTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    fun getMonthlyExpenseReport(): Flow<List<CategoryExpense>>
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun getRecurringTransactions(): Flow<List<Transaction>>
    suspend fun getMonthlyIncome(): Double
    suspend fun getExpenseSummary(start: Long, end: Long): Flow<Map<String, Double>>
}