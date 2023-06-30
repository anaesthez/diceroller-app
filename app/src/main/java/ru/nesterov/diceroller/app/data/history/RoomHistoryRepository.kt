package ru.nesterov.diceroller.app.data.history

import ru.nesterov.diceroller.app.data.history.entities.HistoryElementDbEntity
import ru.nesterov.diceroller.app.domain.entity.HistoryResult
import ru.nesterov.diceroller.app.domain.repository.History

class RoomHistoryRepository(
    private val historyDao: HistoryDao
) : History {

    override suspend fun saveRollResult(historyResult: HistoryResult) {
        val entity = HistoryElementDbEntity.fromHistoryResult(historyResult)
        historyDao.saveRollResult(entity)
    }

    override suspend fun getRollResultList(): List<HistoryResult> =
        historyDao.getRollResultList().map { it.toHistoryResult() }


    override suspend fun deleteRollResult(elementToDelete: HistoryResult) {
        val entity = HistoryElementDbEntity.fromHistoryResult(elementToDelete)
        historyDao.deleteRollResult(entity)
    }
}