package ru.nesterov.diceroller.app.presentation.cubes

import core.views.BaseViewModel
import ru.nesterov.diceroller.app.domain.entity.DiceResult
import ru.nesterov.diceroller.app.domain.repository.Cubes
import ru.nesterov.diceroller.app.domain.repository.Roll
import ru.nesterov.diceroller.app.domain.usecase.roll.GetCubesUseCase
import ru.nesterov.diceroller.app.domain.usecase.roll.ReRollResultUseCase
import kotlin.random.Random
import kotlin.random.nextInt

class CubesViewModel(
    roll: Roll,
    cubes: Cubes
): BaseViewModel() {


    private val reRollResultUseCase = ReRollResultUseCase(roll)
    private val getCubesUseCase = GetCubesUseCase(cubes)

    val cubesList = getCubesUseCase.getCubes()

    fun reRollDice(diceResult: DiceResult) {
        val newResult = generateNewResult(diceResult.faces)
        reRollResultUseCase.reRollDice(diceResult, newResult, diceResult.result)
    }

    private fun generateNewResult(faces: Int): Int = Random.nextInt(1..faces)
}