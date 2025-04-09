package com.example.financetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.data.sync.SyncWorker
import com.example.financetracker.ui.navigation.AppNavigation
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleWorkers()
        enableEdgeToEdge()
        setContent {
            FinanceTrackerTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
                }
            }
        }

    private fun scheduleWorkers() {
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