package ru.nesterov.diceroller.app.domain.usecase.roll

import ru.nesterov.diceroller.app.domain.repository.Roll

class RollDiceUseCase(private val roll: Roll) {

    fun rollDice(faces: Int, numberOfRolls: Int) =
        roll.rollDice(faces, numberOfRolls)

}