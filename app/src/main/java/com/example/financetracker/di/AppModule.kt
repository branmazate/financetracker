package com.example.financetracker.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.financetracker.data.db.AppDatabase
import com.example.financetracker.data.db.dao.AccountDao
import com.example.financetracker.data.db.dao.DebtDao
import com.example.financetracker.data.db.dao.TransactionDao
import com.example.financetracker.data.repository.AccountRepository
import com.example.financetracker.data.repository.AccountRepositoryImpl
import com.example.financetracker.data.repository.DebtRepository
import com.example.financetracker.data.repository.DebtRepositoryImpl
import com.example.financetracker.data.repository.TransactionRepository
import com.example.financetracker.data.repository.TransactionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "finance.db"
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    //TODO add default data
                }
            }
        ).build()
    }

    @Provides
    fun provideTransactionRepository(
        dao: TransactionDao,
        accountDao: AccountDao,
        dispatcher: CoroutineDispatcher
    ): TransactionRepository = TransactionRepositoryImpl(dao, accountDao, dispatcher)

    @Provides
    fun provideAccountRepository(dao:AccountDao): AccountRepository = AccountRepositoryImpl(dao)

    @Provides
    fun provideDebtRepository(dao:DebtDao): DebtRepository = DebtRepositoryImpl(dao)
}