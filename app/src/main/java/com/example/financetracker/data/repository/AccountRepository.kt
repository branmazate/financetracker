package com.example.financetracker.data.repository

import com.example.financetracker.data.db.dao.AccountDao
import com.example.financetracker.data.model.AccountWithTransactions
import com.example.financetracker.data.model.BankAccount
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override fun getAllAccounts(): Flow<List<BankAccount>> {
        return accountDao.getAllAccounts()
    }
    override suspend fun getAccountWithTransactions(accountId: Long): Flow<AccountWithTransactions> {
        return accountDao.getAccountWithTransactions(accountId)
    }
    override suspend fun createAccount(account: BankAccount): Long {
        if (account.balance < 0 && account.type != BankAccount.AccountType.CREDIT_CARD) {
            throw IllegalArgumentException("Invalid balance for non-credit card account")
        }
        return accountDao.insert(account)
    }
    override suspend fun convertCurrency(
        accountId: Long,
        newCurrency: String,
        exchangeRate: Double
    ) {
        val account = accountDao.getAccountById(accountId) ?: throw Exception("Account not found")
        val convertedBalance = account.balance * exchangeRate

        accountDao.update(
            account.copy(
                balance = convertedBalance,
                currency = newCurrency
            )
        )
    }

    override suspend fun getAccountBalance(
        accountId:Long
    ) :Double {
        val account = accountDao.getAccountById(accountId)
        return account.balance
    }

    override suspend fun updateBalance(
        accountId: Long,
        newBalance: Double
    ){
        val account = accountDao.getAccountById(accountId) ?: throw Exception("Account not found")
        accountDao.update(
            account.copy(
                balance = newBalance
            )
        )
    }

    //Function to get the total balance from cash and checking accounts
    //TODO substitute with a more versatile function to get the balance of every account type
    override fun getTotalBalanceFromCashAndCheckingAccounts(): Flow<Double> {
        return accountDao.getTotalCheckingAndCashBalance()
    }
}

interface AccountRepository {
    fun getAllAccounts(): Flow<List<BankAccount>>
    suspend fun getAccountWithTransactions(accountId: Long): Flow<AccountWithTransactions>
    suspend fun createAccount(account: BankAccount): Long
    suspend fun convertCurrency(accountId: Long, newCurrency: String, exchangeRate: Double)
    suspend fun getAccountBalance(accountId: Long):Double
    suspend fun updateBalance(accountId: Long, newBalance: Double)
    fun getTotalBalanceFromCashAndCheckingAccounts(): Flow<Double>
}