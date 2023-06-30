package ru.nesterov.diceroller.app.presentation.roll.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nesterov.diceroller.app.domain.repository.Roll
import ru.nesterov.diceroller.app.domain.usecase.roll.AddDiceUseCase
import core.views.BaseViewModel

class AddViewModel(
    roll: Roll
): BaseViewModel()  {

    private val _errorInputSides = MutableLiveData<Boolean>()
    val errorInputSides: LiveData<Boolean>
        get() = _errorInputSides

    private val addDiceUseCase = AddDiceUseCase(roll)

    fun addNewDice(faces: String) {
        val sides = parseInputSides(faces)
        if (sides != null && validateSides(sides)) {
            addDiceUseCase.addDice(sides)
        } else {
            _errorInputSides.value = true
        }
    }

    private fun validateSides(sides: Int?): Boolean {
        return (sides != null) && sides !in listOf(
            DEFAULT_SIDE_20,
            DEFAULT_SIDE_12,
            DEFAULT_SIDE_10,
            DEFAULT_SIDE_8,
            DEFAULT_SIDE_6,
            DEFAULT_SIDE_4
        )
    }

    fun resetInputSides() {
        _errorInputSides.value = false
    }

    private fun parseInputSides(sides: String): Int? =
        sides.trim().toIntOrNull()

    companion object {

        private const val DEFAULT_SIDE_20 = 20
        private const val DEFAULT_SIDE_12 = 12
        private const val DEFAULT_SIDE_10 = 10
        private const val DEFAULT_SIDE_8 = 8
        private const val DEFAULT_SIDE_6 = 6
        private const val DEFAULT_SIDE_4 = 4
    }

}