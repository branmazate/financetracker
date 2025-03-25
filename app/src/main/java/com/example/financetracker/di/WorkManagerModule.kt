package com.example.financetracker.di

import com.example.financetracker.data.repository.AccountRepository
import com.example.financetracker.data.repository.DebtRepository
import com.example.financetracker.data.repository.TransactionRepository
import com.example.financetracker.data.sync.FinanceSyncManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class WorkManagerModule {
    @Provides
    fun provideSyncManager(
        transactionRepo: TransactionRepository,
        accountRepo: AccountRepository,
        debtRepo: DebtRepository
    ): FinanceSyncManager{
        return FinanceSyncManager(
            accountRepo,
            debtRepo,
            transactionRepo
        )
    }
}