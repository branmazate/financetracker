package com.example.financetracker.data.repository

import com.example.financetracker.data.db.dao.DebtDao
import com.example.financetracker.data.model.Debt
import com.example.financetracker.utils.ValidationHelper
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class DebtRepositoryImpl @Inject constructor(
    private val debtDao: DebtDao
) : DebtRepository {
    /**
     * Adds a new debt to the data source.
     *
     * This function creates a new `Debt` object with the provided details and inserts it into the database.
     * It performs validation on the debt data before insertion.
     *
     * @param type The type of the debt (e.g., LOAN, CREDIT_CARD).
     * @param creditor The name of the creditor associated with the debt.
     * @param startDate The date when the debt was incurred or started.
     * @param endDate The date when the debt is expected to be fully paid or expires.
     * @param dueDate The date when the next payment is due.
     * @param debt The debt object for validation purposes. should be the same type as the `type`
     * @param totalAmount The total amount of the debt.
     * @param interestRate The interest rate applied to the debt.
     *
     * @throws IllegalArgumentException If the provided debt data is invalid according to the `ValidationHelper.isValidDebt` method.
     *///Function to add a new debt
    override suspend fun addNewDebt(
        type: Debt.DebtType,
        creditor: String,
        startDate: Date,
        endDate: Date,
        dueDate: Date,
        debt: Debt,
        totalAmount: Double,
        interestRate: Double
    ) {
        if (!ValidationHelper.isValidDebt(
                debt = debt,
                totalAmount = totalAmount,
                interestRate = interestRate
            )
        ) {
            throw IllegalArgumentException("Invalid debt data")
        }
        val newDebt = Debt(
            type = type,
            creditor = creditor,
            totalAmount = totalAmount,
            remainingAmount = totalAmount,
            interestRate = interestRate,
            startDate = startDate,
            endDate = endDate,
            dueDate = dueDate,
            payments = emptyList(),
            status = Debt.DebtStatus.ACTIVE
        )
        debtDao.insert(newDebt)
    }
    //Function to delete a debt
    override suspend fun deleteDebt(debt: Debt) {
        debtDao.delete(debt)
    }

    //Function to get all the debts
    override fun getAllDebts(): Flow<List<Debt>> {
        return debtDao.getAllDebts()
    }
}
interface DebtRepository{
    suspend fun addNewDebt(
        type: Debt.DebtType,
        creditor: String,
        startDate: Date,
        endDate: Date,
        dueDate: Date,
        debt: Debt,
        totalAmount: Double,
        interestRate: Double
    )
    suspend fun deleteDebt(debt: Debt)
    fun getAllDebts(): Flow<List<Debt>>
}