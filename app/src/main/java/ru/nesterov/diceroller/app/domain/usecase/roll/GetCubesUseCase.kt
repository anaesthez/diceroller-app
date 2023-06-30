package ru.nesterov.diceroller.app.domain.usecase.roll

import androidx.lifecycle.LiveData
import ru.nesterov.diceroller.app.domain.entity.DiceResult
import ru.nesterov.diceroller.app.domain.repository.Cubes

class GetCubesUseCase(private val cubes: Cubes) {

    fun getCubes(): LiveData<List<DiceResult>> = cubes.getCubes()

}