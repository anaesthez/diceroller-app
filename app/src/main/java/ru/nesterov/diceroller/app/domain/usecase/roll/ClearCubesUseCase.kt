package ru.nesterov.diceroller.app.domain.usecase.roll

import ru.nesterov.diceroller.app.domain.repository.Cubes

class ClearCubesUseCase(private val rollRepository: Cubes) {

    fun clearCubes() {
        rollRepository.clearCubes()
    }

}