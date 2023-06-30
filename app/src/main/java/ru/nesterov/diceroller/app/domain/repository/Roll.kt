package ru.nesterov.diceroller.app.domain.repository

import androidx.lifecycle.LiveData
import core.model.Repository
import ru.nesterov.diceroller.app.domain.entity.Dice
import ru.nesterov.diceroller.app.domain.entity.DiceResult

interface Roll : Repository {

    fun addDice(faces: Int)

    fun deleteDice(dice: Dice)

    fun getDiceList(): LiveData<List<Dice>>

    fun selectDice(dice: Dice)

    fun reRollDice(diceResult: DiceResult, newResult: Int, oldResult: Int)

    fun rollDice(faces: Int, numberOfRolls: Int)

    fun getRollResult(): List<DiceResult>

}