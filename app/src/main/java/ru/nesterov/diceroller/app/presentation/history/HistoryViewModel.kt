package ru.nesterov.diceroller.app.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ru.nesterov.diceroller.app.domain.entity.HistoryResult
import ru.nesterov.diceroller.app.domain.repository.History
import ru.nesterov.diceroller.app.domain.usecase.history.DeleteRollResultUseCase
import ru.nesterov.diceroller.app.domain.usecase.history.GetRollResultListUseCase
import core.views.BaseViewModel
import kotlinx.coroutines.launch

class HistoryViewModel(
    historyRepository: History
): BaseViewModel() {

    private val getRollResultListUseCase = GetRollResultListUseCase(historyRepository)
    private val deleteRollResultUseCase = DeleteRollResultUseCase(historyRepository)

    private val _historyList = MutableLiveData<List<HistoryResult>>()
    val historyList: LiveData<List<HistoryResult>>
     get() = _historyList

    init {
        viewModelScope.launch {
            val rollResultList = getRollResultListUseCase.getRollResultList()
            _historyList.value = rollResultList
        }
    }

    fun deleteRollResult(elementToDelete: HistoryResult) {
        viewModelScope.launch {
            deleteRollResultUseCase.deleteRollResult(elementToDelete)
            val newResultList = getRollResultListUseCase.getRollResultList()
            _historyList.value = newResultList
        }
    }
}