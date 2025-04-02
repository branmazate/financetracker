package com.example.financetracker.di

import android.content.Context
import androidx.work.WorkManager
import com.example.financetracker.data.repository.AccountRepository
import com.example.financetracker.data.repository.DebtRepository
import com.example.financetracker.data.repository.TransactionRepository
import com.example.financetracker.data.sync.FinanceSyncManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
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