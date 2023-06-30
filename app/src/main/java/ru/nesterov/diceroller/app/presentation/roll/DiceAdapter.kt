package ru.nesterov.diceroller.app.presentation.roll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.nesterov.diceroller.R
import ru.nesterov.diceroller.app.domain.entity.Dice

class DiceAdapter: ListAdapter<Dice, DiceViewHolder>(DiceDiffCallback()) {

    var onItemClickListener: ((Dice) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )
        return DiceViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).selected)
            R.layout.item_selected_dice
        else
            R.layout.item_unselected_dice

    override fun onBindViewHolder(holder: DiceViewHolder, position: Int) {
        val dice = getItem(position)
        holder.image.setImageResource(dice.image)
        holder.view.setOnClickListener {
            onItemClickListener?.invoke(dice)
        }
    }
}