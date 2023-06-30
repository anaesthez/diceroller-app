package ru.nesterov.diceroller.app.presentation.roll.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.nesterov.diceroller.app.data.roll.RollImpl
import core.utils.viewModelCreator
import ru.nesterov.diceroller.R
import ru.nesterov.diceroller.databinding.FragmentAddBinding

class AddFragment : DialogFragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding: FragmentAddBinding
        get() = _binding ?: throw RuntimeException(getString(R.string.fragmentaddbinding_null))

    private val viewModel: AddViewModel by viewModelCreator { AddViewModel(RollImpl) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpChangedListener()
        setupClickListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputSides.observe(viewLifecycleOwner) {
            if (it) {
                binding.editTextContainer.error = getString(R.string.occupied_value)
            }
        }
    }

    private fun setupClickListener() {
        binding.saveButton.setOnClickListener {
            binding.sidesEditText.text?.toString()?.let {
                    input -> viewModel.addNewDice(input)
            }
        }
    }

    private fun setUpChangedListener() {
        binding.sidesEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputSides()

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}