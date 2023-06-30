package ru.nesterov.diceroller.app.domain.usecase.history

import ru.nesterov.diceroller.app.domain.entity.HistoryResult
import ru.nesterov.diceroller.app.domain.repository.History

class GetRollResultListUseCase(private val history: History) {

    suspend fun getRollResultList(): List<HistoryResult> {
        return history.getRollResultList()
    }

}