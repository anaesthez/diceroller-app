package ru.nesterov.diceroller.app.domain.repository

import core.model.Repository
import ru.nesterov.diceroller.app.domain.entity.HistoryResult

interface History : Repository {

    suspend fun saveRollResult(historyResult: HistoryResult)

    suspend fun getRollResultList(): List<HistoryResult>

    suspend fun deleteRollResult(elementToDelete: HistoryResult)

}