package ru.nesterov.diceroller.app.presentation.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.nesterov.diceroller.R
import ru.nesterov.diceroller.app.domain.entity.HistoryResult
import ru.nesterov.diceroller.databinding.ItemRollResultBinding
import java.text.DateFormat.getDateTimeInstance
import java.util.Calendar

class HistoryAdapter: ListAdapter<HistoryResult, HistoryViewHolder>(HistoryDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemRollResultBinding.inflate(inflater, parent, false)

        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyElement = getItem(position)
        with(holder.binding) {
            diceItemDiceView.setDiceImage(historyElement.image)
            diceItemDiceView.setDiceText(historyElement.result.toString())
            numberOfThrowsTv.text = String.format(
                context.getString(R.string.your_result),
                historyElement.rolls
            )
            resultTextView.text = String.format(
                context.getString(R.string.your_result_card),
                historyElement.result
            )
            idTextView.text = getFormattedTime()

        }
    }

    private fun getFormattedTime(): String {
        val time = Calendar.getInstance().time
        val formatter = getDateTimeInstance()
        return formatter.format(time)
    }
}