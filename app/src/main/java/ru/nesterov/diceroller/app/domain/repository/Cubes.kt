package ru.nesterov.diceroller.app.domain.repository

import androidx.lifecycle.LiveData
import core.model.Repository
import ru.nesterov.diceroller.app.domain.entity.DiceResult

interface Cubes : Repository {

    fun getCubes(): LiveData<List<DiceResult>>

    fun clearCubes()

}