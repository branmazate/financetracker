package com.example.financetracker.data.sync

import com.example.financetracker.data.repository.AccountRepository
import com.example.financetracker.data.repository.DebtRepository
import com.example.financetracker.data.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FinanceSyncManager @Inject constructor(
    private val accountRepo: AccountRepository,
    private val debtRepo: DebtRepository,
    private val transactionRepo: TransactionRepository
) {
    private val syncScope = CoroutineScope(Dispatchers.IO)

    fun fullSync() {
        syncScope.launch {
            try {
                syncAccounts()
                syncTransactions()
                syncDebts()
            } catch (e: Exception){
                handleSyncError(e)
            }
        }
    }

    private suspend fun syncAccounts(){
        val accounts = accountRepo.getAllAccounts().first()
        accounts.forEach { account ->
            val currentBalance = accountRepo.getAccountBalance(account.id)
            accountRepo.updateBalance(account.id, currentBalance)
        }
    }

    private suspend fun syncTransactions(){
        val recurringTransactions = transactionRepo.getRecurringTransactions()
        //TODO Add logic to update recurring transactions
        }

    private suspend fun syncDebts(){
        val activeDebts = debtRepo.getActiveDebts().first()
        //TODO Add logic to check if a debt is overdue and apply interest if so
    }

    private fun handleSyncError(error: Throwable){

    }
}