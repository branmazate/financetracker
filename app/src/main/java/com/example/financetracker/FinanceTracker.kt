package com.example.financetracker

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.data.sync.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class FinanceTracker : Application() {
    @Inject lateinit var workManager: WorkManager

    override fun onCreate(){
        super.onCreate()
        scheduleWorkers()
    }
    private fun scheduleWorkers(){
        val request = PeriodicWorkRequestBuilder<SyncWorker>(
            24,
            TimeUnit.HOURS
        ).build()
        workManager.enqueueUniquePeriodicWork(
            "FinanceSyncWorker",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}