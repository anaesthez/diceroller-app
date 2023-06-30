package ru.nesterov.diceroller.app.domain.usecase.roll

import ru.nesterov.diceroller.app.domain.entity.Dice
import ru.nesterov.diceroller.app.domain.repository.Roll

class SelectDiceUseCase(private val roll: Roll) {

    fun selectDice(dice: Dice) =
        roll.selectDice(dice)

}