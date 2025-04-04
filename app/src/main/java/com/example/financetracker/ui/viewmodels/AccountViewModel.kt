package com.example.financetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.financetracker.data.model.BankAccount
import com.example.financetracker.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepo: AccountRepository
) : ViewModel() {

    val allAccounts: Flow<List<BankAccount>> =
        accountRepo.getAllAccounts()

    fun createAccount(account: BankAccount) {
        accountRepo.createAccount(account)
    }

}