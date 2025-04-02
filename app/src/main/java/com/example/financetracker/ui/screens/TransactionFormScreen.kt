package com.example.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.financetracker.data.model.Transaction
import com.example.financetracker.data.model.TransactionType
import com.example.financetracker.ui.viewmodels.TransactionViewModel
import java.util.Date

@Composable
fun TransactionFormScreen(viewModel: TransactionViewModel) {
    var amount by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(TransactionType.INCOME) }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row(verticalAlignment = Alignment.CenterVertically){
            Text("Type: ${type.name}")
            Switch(
                checked = (type == TransactionType.INCOME),
                onCheckedChange = { isChecked ->
                    type = if (isChecked) TransactionType.INCOME else TransactionType.EXPENSE
                })
        }

        Button(
            onClick = {
                val transaction = Transaction(
                    type = type,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    categoryId = 1,
                    accountId = 1, // Temporal
                    date = Date()
                )
                viewModel.addTransaction(transaction)
            }
        ) {
            Text("Save")
        }
    }
}