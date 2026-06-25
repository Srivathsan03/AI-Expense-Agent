package com.sri.aiexpenseagent.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query(
        """
            SELECT * FROM expenses
            WHERE title LIKE '%' || :query || '%'
            ORDER BY createdAt DESC
        """
    )
    fun searchExpenses(query:String):Flow<List<ExpenseEntity>>

    @Delete
    suspend fun deleteEntity(entity: ExpenseEntity)

    @Query("DELETE FROM expenses")
    suspend fun deleteAll()
}