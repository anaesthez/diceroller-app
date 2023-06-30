package ru.nesterov.diceroller.app.presentation.cubes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.nesterov.diceroller.app.domain.entity.DiceResult
import ru.nesterov.diceroller.app.presentation.custom.ItemDiceView
import ru.nesterov.diceroller.databinding.ItemCubesBinding

class CubesAdapter: ListAdapter<DiceResult, CubesViewHolder>(CubesDiffCallback()) {

    var onItemClickListener: ((DiceResult) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CubesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCubesBinding.inflate(inflater, parent, false)

        return CubesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CubesViewHolder, position: Int) {
        val cubeElement = getItem(position)
        with(holder.binding) {
            cubeItemDiceView.setDiceImage(cubeElement.imageEmpty)
            cubeItemDiceView.setDiceText(cubeElement.result.toString())
            setPaddings(cubeElement.faces, cubeItemDiceView)
            cubeItemDiceView.setListener {
                onItemClickListener?.invoke(cubeElement)
            }
        }
    }

    private fun setPaddings(numberOfFaces: Int, diceView: ItemDiceView) {
        with(diceView) {
            when(numberOfFaces) {
                4 -> {
                    setDicePadding(0, 0, 202, 160)
                    setDiceTextSize(18F)
                }
                6 -> {
                    setDicePadding(0, 0, 205, 190)
                    setDiceTextSize(28F)
                }
                8 -> {
                    setDicePadding(0, 0, 202, 200)
                    setDiceTextSize(24F)
                }
                10 -> {
                    setDicePadding(0, 0, 202, 225)
                    setDiceTextSize(20F)
                }
                12 -> {
                    setDicePadding(0, 0, 200, 200)
                    setDiceTextSize(20F)
                }
                20 -> {
                    setDicePadding(0, 0, 198, 200)
                    setDiceTextSize(15F)
                }
                else -> {
                    setDicePadding(0, 0, 198, 200)
                    setDiceTextSize(40F)
                }
            }
        }
    }
}