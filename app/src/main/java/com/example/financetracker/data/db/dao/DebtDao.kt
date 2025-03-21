package com.example.financetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.financetracker.data.model.Debt
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(debt: Debt): Long

    @Update
    suspend fun update(debt: Debt)

    @Delete
    suspend fun delete(debt: Debt)

    //get all the debts
    @Query("SELECT * FROM debts ORDER BY dueDate ASC")
    fun getAllDebts(): Flow<List<Debt>>

    //Filter by overdue Debt
    @Query("""
        SELECT * FROM debts
        WHERE status = 'OVERDUE'
        ORDER BY dueDate ASC
    """)
    //FIXME Verify if is better making the distinction between overdue and active status or just make the distinction in the query
    fun getOverdueDebts(): Flow<List<Debt>>

    //Get the active debts and its payments
    //TODO function to get active debts with payments

    //Apply a payment
    @Query("""
        UPDATE debts
        SET remainingAmount = remainingAmount - :amount
        WHERE id = :debtId
    """)
    fun applyPayment(debtId: Long, amount: Double)

    //Update debt status
    @Query("""
        UPDATE debts
        SET remainingAmount = remainingAmount * (1 + interestRate)/100
        WHERE status = 'ACTIVE'
        AND dueDate < :currentDate
    """)
    fun updateDebtStatus(currentDate: Long)
    abstract fun getActiveDebts(): Flow<List<Debt>>

    //Get debt by id
    @Query("""
        SELECT * FROM debts
        WHERE id = :debtId
    """)
    fun getDebtById(debtId: Long): Flow<Debt>
}