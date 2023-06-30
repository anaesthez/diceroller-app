package ru.nesterov.diceroller.app.domain.usecase.roll

import ru.nesterov.diceroller.app.domain.entity.Dice
import ru.nesterov.diceroller.app.domain.repository.Roll

class DeleteDiceUseCase(private val roll: Roll) {

    fun deleteDice(dice: Dice) {
        roll.deleteDice(dice)
    }
}