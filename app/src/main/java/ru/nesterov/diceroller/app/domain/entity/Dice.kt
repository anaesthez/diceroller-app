package ru.nesterov.diceroller.app.domain.entity

data class Dice(
    var id: Int = UNDEFINED_ID,
    var selected: Boolean,
    val imageEmpty: Int,
    val image: Int,
    val faces: Int
) {

    companion object {

        const val UNDEFINED_ID = -1

        const val DICE20_VALUE = 20
        const val DICE12_VALUE = 12
        const val DICE10_VALUE = 10
        const val DICE8_VALUE = 8
        const val DICE6_VALUE = 6
        const val DICE4_VALUE = 4
        const val DICE_ADD = 0
    }
}
