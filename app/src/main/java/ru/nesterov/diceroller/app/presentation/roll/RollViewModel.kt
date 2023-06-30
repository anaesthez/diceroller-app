package ru.nesterov.diceroller.app.presentation.roll

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ru.nesterov.diceroller.app.domain.entity.Dice
import ru.nesterov.diceroller.app.domain.entity.DiceResult
import ru.nesterov.diceroller.app.domain.entity.HistoryResult
import ru.nesterov.diceroller.app.domain.repository.Cubes
import ru.nesterov.diceroller.app.domain.repository.History
import ru.nesterov.diceroller.app.domain.repository.Roll
import ru.nesterov.diceroller.app.domain.usecase.roll.ClearCubesUseCase
import ru.nesterov.diceroller.app.domain.usecase.roll.GetDiceListUseCase
import ru.nesterov.diceroller.app.domain.usecase.roll.GetRollResultUseCase
import ru.nesterov.diceroller.app.domain.usecase.roll.RollDiceUseCase
import ru.nesterov.diceroller.app.domain.usecase.history.SaveRollResultUseCase
import ru.nesterov.diceroller.app.domain.usecase.roll.DeleteDiceUseCase
import ru.nesterov.diceroller.app.domain.usecase.roll.SelectDiceUseCase
import core.views.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis

class RollViewModel(
    roll: Roll,
    history: History,
    cubes: Cubes
): BaseViewModel() {

    private val getDiceListUseCase = GetDiceListUseCase(roll)
    private val selectDiceUseCase = SelectDiceUseCase(roll)
    private val rollDiceUseCase = RollDiceUseCase(roll)
    private val getRollResultUseCase = GetRollResultUseCase(roll)
    private val deleteDiceUseCase = DeleteDiceUseCase(roll)
    private val saveRollResultUseCase = SaveRollResultUseCase(history)
    private val clearCubesUseCase = ClearCubesUseCase(cubes)

    private val _errorInput = MutableLiveData<Boolean>()

    private val _state = MutableLiveData<State>()

    val state: LiveData<State>
        get() = _state
    private val currentState: State
        get() = state.value!!
    val errorInput: LiveData<Boolean>
        get() = _errorInput

    val diceList = getDiceListUseCase.getDiceList()

    init {
        _state.value = State(
            dice = getDice(),
            numberOfRolls = 0,
            rollingResult = 0,
            shouldShowAttention = false
        )
        selectDice(getDice())
    }

    private fun getDice(): Dice =
        diceList.value?.get(0) ?: throw RuntimeException()


    fun selectDice(dice: Dice) {
        val selectedDice = dice.copy(selected = true)
        selectDiceUseCase.selectDice(selectedDice)
        _state.value = currentState.copy(dice = dice, rollingResult = 0)
    }

    fun setNumberOfRolls(value: String) {
        if (validateInput(value)) {
            _state.value = currentState.copy(numberOfRolls = parseInput(value)
                ?: currentState.numberOfRolls)
        } else {
            _state.value = currentState.copy(numberOfRolls = parseInput(value)
                ?: currentState.numberOfRolls
            )
        }
    }

    private fun validateInput(value: String): Boolean =
        value.isEmpty()

    private fun parseInput(value: String): Int? =
        value.toIntOrNull()

    fun incrementNumberOfRolls() {
        if (currentState.numberOfRolls < 0) return
        _state.value = currentState.copy(numberOfRolls = currentState.numberOfRolls.plus(1))
    }

    fun decrementNumberOfRolls() {
        if (currentState.numberOfRolls <= 0) return
        _state.value = currentState.copy(numberOfRolls = currentState.numberOfRolls.plus(-1))
    }

    fun deleteDice(dice: Dice) {
        deleteDiceUseCase.deleteDice(dice)
    }

    fun rollTheDice() {
        if (currentState.numberOfRolls != 0) {
            clearCubesUseCase.clearCubes()
            rollDiceUseCase.rollDice(
                numberOfRolls = currentState.numberOfRolls,
                faces = currentState.dice.faces
            )
            val result = getRollResultUseCase.getRollResult()
            val newState = currentState.copy(
                rollingResult = result.sumOf { it.result },
                shouldShowAttention = result.shouldShow()
            )
            _state.value = newState
            saveResult()
        } else {
            _state.value = currentState.copy(rollingResult = 0)
        }
    }

    fun applyResult() {
        val result = getRollResultUseCase.getRollResult()
        val newState = currentState.copy(
            rollingResult = result.sumOf { it.result },
            shouldShowAttention = result.shouldShow()
        )
        _state.value = newState
    }

    private fun List<DiceResult>.shouldShow() =
        this.isNotEmpty() && this.any { it.result == 1 || it.result == 2 }

    private fun saveResult() {
        viewModelScope.launch {
            saveRollResultUseCase.saveRollResultUseCase(
                HistoryResult(
                    id = 0,
                    image = currentState.dice.image,
                    rolls = currentState.numberOfRolls,
                    result = currentState.rollingResult,
                    createdAt = currentTimeMillis()
                )
            )
        }
    }

    fun resetInputName() {
        _errorInput.value = false
    }

    data class State(
        val shouldShowAttention: Boolean,
        val dice: Dice,
        val numberOfRolls: Int,
        var rollingResult: Int
    )
}