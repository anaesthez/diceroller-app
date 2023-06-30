package ru.nesterov.diceroller.app.data.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.nesterov.diceroller.app.data.history.entities.HistoryElementDbEntity

@Dao
interface HistoryDao {

    @Insert
    suspend fun saveRollResult(historyElementDbEntity: HistoryElementDbEntity)

    @Delete
    suspend fun deleteRollResult(historyElementDbEntity: HistoryElementDbEntity)

    @Query("SELECT * FROM history" )
    suspend fun getRollResultList(): List<HistoryElementDbEntity>

}