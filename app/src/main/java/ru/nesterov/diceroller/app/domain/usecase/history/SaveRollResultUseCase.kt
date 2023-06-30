package ru.nesterov.diceroller.app.domain.usecase.history

import ru.nesterov.diceroller.app.domain.entity.HistoryResult
import ru.nesterov.diceroller.app.domain.repository.History

class SaveRollResultUseCase(private val history: History) {

    suspend fun saveRollResultUseCase(historyResult: HistoryResult) =
        history.saveRollResult(historyResult)

}