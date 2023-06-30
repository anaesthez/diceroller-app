package ru.nesterov.diceroller.app.domain.usecase.roll

import ru.nesterov.diceroller.app.domain.entity.DiceResult
import ru.nesterov.diceroller.app.domain.repository.Roll

class GetRollResultUseCase(private val roll: Roll) {

    fun getRollResult(): List<DiceResult> =
        roll.getRollResult()

}