package ru.nesterov.diceroller.app.domain.usecase.history

import ru.nesterov.diceroller.app.domain.entity.HistoryResult
import ru.nesterov.diceroller.app.domain.repository.History

class DeleteRollResultUseCase(private val history: History) {

    suspend fun deleteRollResult(elementToDelete: HistoryResult) =
        history.deleteRollResult(elementToDelete)

}