package ru.nesterov.diceroller.app.domain.entity

data class DiceResult(
    var id: Int = UNDEFINED_ID,
    val imageEmpty: Int,
    val faces: Int,
    val result: Int
) {

    companion object {

        const val UNDEFINED_ID = -1
    }
}
