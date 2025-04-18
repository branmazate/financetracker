package com.example.financetracker.data.db.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.financetracker.data.model.Transaction
import kotlinx.coroutines.flow.Flow

data class CategoryExpense(
    @ColumnInfo(name = "categoryId") val categoryId: Long,
    @ColumnInfo(name = "total") val total: Double
)
@Dao
interface TransactionDao {
    //basic operations
    @Insert
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction)

   @Delete
   suspend fun delete(transaction: Transaction)

   //Consults
   @Query("SELECT * FROM transactions ORDER by date DESC")
   fun getAllTransactions(): Flow<List<Transaction>>

   @Query("SELECT * FROM transactions WHERE id = :id")
   suspend fun getTransactionById(id: Long): Transaction?

   //Consults by account and date
   @Query("""
       SELECT * FROM transactions
       WHERE accountId = :accountId
       AND date BETWEEN :startDate AND :endDate
       ORDER BY date DESC
   """)
   fun getTransactionsByAccountAndDate(
       accountId: Long,
       startDate: Long,
       endDate: Long
   ): Flow<List<Transaction>>

   @Query("""
       SELECT * FROM transactions
       WHERE accountId = :accountId
   """)
   fun getTransactionsByAccountId(accountId: Long): Flow<List<Transaction>>

   @Query("""
       SELECT SUM(amount) FROM transactions
       WHERE type = 'INCOME'
       AND accountId = :accountId
       AND date BETWEEN :startDate AND :endDate
   """)
    suspend fun getTotalIncomeForAccount(
        accountId: Long,
        startDate: Long,
        endDate: Long
    ): Double

    @Query("""
        SELECT categoryId, SUM(amount) AS total
        FROM transactions
        WHERE type = 'EXPENSE'
        AND date BETWEEN :start AND :end
        GROUP BY categoryId
    """)
    fun getExpenseSummaryByCategory(
        start: Long,
        end: Long
    ): Flow<List<CategoryExpense>>

    //For recurrent transactions
    @Query("SELECT * FROM transactions WHERE recurring = 1")
    fun getRecurringTransactions(): Flow<List<Transaction>>

    //Update account
    @Query("UPDATE transactions SET accountId = :newAccountId WHERE accountId = :oldAccountId")
    suspend fun updateAccount(oldAccountId: Long, newAccountId: Long)

    //Get monthly income
    @Query("""
        SELECT SUM(amount) 
        FROM transactions
        WHERE type = 'INCOME'
        AND recurring = 1
        AND recurrenceInterval = 'MONTHLY'
    """)
    suspend fun getMonthlyIncome(): Double


    //Full-text search
    @Query("""SELECT * FROM transactions 
        WHERE description LIKE '%' || :query || '%'
        OR categoryId LIKE '%' || :query || '%'
        """)
    fun searchTransactions(query: String): Flow<List<Transaction>>

}