package ru.nesterov.diceroller.app.presentation.roll

import androidx.recyclerview.widget.DiffUtil
import ru.nesterov.diceroller.app.domain.entity.Dice

class DiceDiffCallback: DiffUtil.ItemCallback<Dice>() {

    override fun areItemsTheSame(oldItem: Dice, newItem: Dice): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Dice, newItem: Dice): Boolean =
        oldItem == newItem

}