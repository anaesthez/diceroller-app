package ru.nesterov.diceroller.app.domain.usecase.roll

import androidx.lifecycle.LiveData
import ru.nesterov.diceroller.app.domain.entity.Dice
import ru.nesterov.diceroller.app.domain.repository.Roll

class GetDiceListUseCase(private val roll: Roll) {

    fun getDiceList(): LiveData<List<Dice>> =
        roll.getDiceList()

}