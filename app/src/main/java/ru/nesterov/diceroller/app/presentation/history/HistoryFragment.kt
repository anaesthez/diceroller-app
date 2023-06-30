package ru.nesterov.diceroller.app.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.nesterov.diceroller.app.Repositories
import core.utils.viewModelCreator
import core.views.BaseFragment
import ru.nesterov.diceroller.R
import ru.nesterov.diceroller.databinding.FragmentHistoryBinding

class HistoryFragment : BaseFragment() {

    override val viewModel: HistoryViewModel by viewModelCreator {
        HistoryViewModel(Repositories.historyRepository)
    }

    private lateinit var historyAdapter: HistoryAdapter

    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() = _binding ?: throw RuntimeException(getString(R.string.fragmenthistorybinding_null))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHistoryRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.historyList.observe(viewLifecycleOwner) {
            historyAdapter.submitList(it)
        }
    }

    private fun setupHistoryRecyclerView() {
        with(binding.historyRecyclerView) {
            historyAdapter = HistoryAdapter()
            adapter = historyAdapter
        }
        setupSwipeListener(binding.historyRecyclerView)
    }

    private fun setupSwipeListener(historyRecyclerViewList: RecyclerView) {
        val callback = object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = historyAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteRollResult(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(historyRecyclerViewList)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}