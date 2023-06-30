package ru.nesterov.diceroller.app.presentation.history

import androidx.recyclerview.widget.DiffUtil
import ru.nesterov.diceroller.app.domain.entity.HistoryResult

class HistoryDiffCallback: DiffUtil.ItemCallback<HistoryResult>() {

    override fun areItemsTheSame(oldItem: HistoryResult, newItem: HistoryResult): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: HistoryResult, newItem: HistoryResult): Boolean =
        oldItem == newItem

}