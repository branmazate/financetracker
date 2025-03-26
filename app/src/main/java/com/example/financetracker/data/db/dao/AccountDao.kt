package com.example.financetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.financetracker.data.model.AccountWithTransactions
import com.example.financetracker.data.model.BankAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert
    suspend fun insert(account: BankAccount): Long

    @Update
    suspend fun update(account: BankAccount)

    @Delete
    suspend fun delete(account: BankAccount)

    @Query("SELECT * FROM bank_accounts ORDER BY name ASC")
    fun getAllAccounts(): Flow<List<BankAccount>>

    @Transaction
    @Query("SELECT * FROM bank_accounts WHERE id = :accountId")
    suspend fun getAccountWithTransactions(accountId: Long): Flow<AccountWithTransactions>

    @Query("UPDATE bank_accounts SET balance = balance + :amount WHERE id = :accountId")
    suspend fun updateBalance(accountId: Long, amount: Double)

    /* Not using different currencies right now so It will be for a future version.
    @Query("""
        SELECT SUM(balance)
        FROM bank_accounts
        WHERE currency = :currency
    """)
    suspend fun getTotalBalanceByCurrency(currency: String): Double

    //Update for conversion rate
    @Query("""
        UPDATE bank_accounts
        SET balance = balance * :conversionRate
        WHERE currency = :originalCurrency
    """)
    suspend fun convertCurrency(originalCurrency: String, conversionRate: Double)
*/
    /*
    //Get accounts with recent activity
    @Transaction
    @Query("""
        SELECT * FROM bank_accounts
        WHERE id IN (
        SELECT DISTINCT id
        FROM transactions
        WHERE date BETWEEN :start AND :end
        )
    """)
    fun getAccountsWithRecentActivity(start: Long, end: Long): Flow<List<AccountWithTransactions>>
     */
/*
    //Validations in operations
    @Update
    suspend fun updateAccountSafely(account: BankAccount){
        if (account.balance < 0 && account.type != BankAccount.AccountType.CREDIT_CARD)
        {
            throw IllegalArgumentException("Negative balance not allowed for this kind of account")
        }
        update(account)
    }
*/
    //Get account by Id
    @Query("""
        SELECT * FROM bank_accounts
        WHERE id = :accountId
    """)
    suspend fun getAccountById(accountId: Long): BankAccount
}