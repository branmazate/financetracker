package com.example.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.financetracker.data.model.BankAccount
import com.example.financetracker.data.model.BankAccount.AccountType
import com.example.financetracker.ui.viewmodels.AccountViewModel

@Composable
fun AccountFormScreen(viewModel: AccountViewModel) {
    var accountName by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = accountName,
            onValueChange = { accountName = it },
            label = { Text("Account Name") }
        )

        Button(
            onClick = {
                viewModel.createAccount(
                    BankAccount(
                        name = accountName,
                        type = AccountType.CHECKING,
                        balance = balance.toDoubleOrNull() ?: 0.0
                    )
                )
            }
        ) {
            Text("Create Account")
        }
    }
}