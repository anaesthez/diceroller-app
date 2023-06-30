package ru.nesterov.diceroller.app.presentation.cubes

import androidx.recyclerview.widget.DiffUtil
import ru.nesterov.diceroller.app.domain.entity.DiceResult

class CubesDiffCallback: DiffUtil.ItemCallback<DiceResult>() {

    override fun areItemsTheSame(oldItem: DiceResult, newItem: DiceResult): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: DiceResult, newItem: DiceResult): Boolean =
        oldItem == newItem
}