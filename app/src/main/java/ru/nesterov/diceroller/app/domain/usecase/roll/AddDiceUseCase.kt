package ru.nesterov.diceroller.app.domain.usecase.roll

import ru.nesterov.diceroller.app.domain.repository.Roll

class AddDiceUseCase(private val roll: Roll) {

    fun addDice(faces: Int) {
        roll.addDice(faces)
    }
}