package ru.nesterov.diceroller.app.presentation.cubes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import ru.nesterov.diceroller.app.Repositories
import core.utils.setSavedStateBoolean
import core.utils.viewModelCreator
import ru.nesterov.diceroller.databinding.FragmentCubesBinding

class CubesFragment : DialogFragment() {

    private val viewModel: CubesViewModel by viewModelCreator {
        CubesViewModel(
            Repositories.rollRepository,
            Repositories.cubesRepository
        )
    }

    private var _binding: FragmentCubesBinding? = null
    private val binding: FragmentCubesBinding
        get() = _binding ?: throw RuntimeException("FragmentCubesBinding == null")

    private lateinit var cubesAdapter: CubesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCubesRecyclerView()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.applyButton.setOnClickListener {
            findNavController().setSavedStateBoolean(SHOULD_SHOW_BUTTON, true)
            dismiss()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun observeViewModel() {
        viewModel.cubesList.observe(viewLifecycleOwner) {
            cubesAdapter.submitList(it)
        }
    }

    private fun setupCubesRecyclerView() {
        with(binding.cubesRecyclerView) {
            cubesAdapter = CubesAdapter()
            adapter = cubesAdapter
        }
        setupOnItemClickListener()
    }

    private fun setupOnItemClickListener() {
        cubesAdapter.onItemClickListener = {
            viewModel.reRollDice(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCubesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        const val SHOULD_SHOW_BUTTON = "should show data"
    }
}