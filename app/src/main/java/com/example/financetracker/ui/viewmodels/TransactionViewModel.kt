package com.example.financetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.data.model.Transaction
import com.example.financetracker.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
): ViewModel() {

    val transactions = repository.getAllTransactions().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addTransaction(transaction: Transaction){
        viewModelScope.launch {
            try {
                repository.addTransaction(transaction)
            } catch(e: Exception) {
                //TODO Logic for handle the exception
            }
        }
    }
}