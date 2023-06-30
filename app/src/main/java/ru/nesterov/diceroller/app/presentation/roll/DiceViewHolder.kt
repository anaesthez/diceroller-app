package ru.nesterov.diceroller.app.presentation.roll

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.nesterov.diceroller.R

class DiceViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.dice_iv)
}