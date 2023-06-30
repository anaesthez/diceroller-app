package ru.nesterov.diceroller.app.domain.usecase.roll

import ru.nesterov.diceroller.app.domain.entity.DiceResult
import ru.nesterov.diceroller.app.domain.repository.Roll

class ReRollResultUseCase(private val roll: Roll) {

    fun reRollDice(diceResult: DiceResult, newResult: Int, oldResult: Int) {
        roll.reRollDice(diceResult, newResult, oldResult)
    }
}