package com.example.financetracker.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.financetracker.data.model.Transaction
import kotlinx.coroutines.flow.Flow

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
    )

    @Query("""
        SELECT category, SUM(amount) AS total
        FROM transactions
        WHERE type = 'EXPENSE'
        AND date BETWEEN :start AND :end
        GROUP BY category
    """)
    fun getExpenseSummaryByCategory(
        start: Long,
        end: Long
    ): Flow<Map<String, Double>>

    //For recurrent transactions
    @Query("SELECT * FROM transactions WHERE recurring = 1")
    fun getRecurringTransactions(): Flow<List<Transaction>>

    //Update account
    @Query("UPDATE transactions SET accountId = :newAccountId WHERE accountId = :oldAccountId")
    suspend fun updateAccount(oldAccountId: Long, newAccountId: Long)

    //Get monthly summary
    @Query("""
        SELECT strftime('%Y-%m', date/1000, 'unixepoch') AS month, 
        SUM(amount) AS total
        FROM transactions
        WHERE type = :type
        GROUP BY month
        ORDER BY month DESC
        LIMIT 12
    """)
    fun getMonthlySummary(type: String): Flow<Map<String, Double>>

    //Get monthly income
    @Query("""
        SELECT SUM(amount) 
        FROM transactions
        WHERE type = 'INCOME'
        AND recurring = 1
        AND recurrenceInterval = 'MONTHLY'
    """)
    suspend fun getMonthlyIncome(): Double

    //Pagination
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getTransactionsPaged(): PagingSource<Int, Transaction>

    //Full-text search
    @Query("""SELECT * FROM transactions 
        WHERE description LIKE '%' || :query || '%'
        OR category LIKE '%' || :query || '%'
        """)
    fun searchTransactions(query: String): Flow<List<Transaction>>

}