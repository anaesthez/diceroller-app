package ru.nesterov.diceroller.app.presentation.roll

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.nesterov.diceroller.app.Repositories
import ru.nesterov.diceroller.app.presentation.cubes.CubesFragment
import core.utils.getCurrentBackStackEntryLiveData
import core.utils.isNotDefault
import core.utils.viewModelCreator
import core.views.BaseFragment
import ru.nesterov.diceroller.R
import ru.nesterov.diceroller.databinding.FragmentRollBinding

class RollFragment : BaseFragment() {

    override val viewModel: RollViewModel by viewModelCreator {
        RollViewModel(
            Repositories.rollRepository,
            Repositories.historyRepository,
            Repositories.cubesRepository
        )
    }

    private lateinit var diceAdapter: DiceAdapter

    private var _binding: FragmentRollBinding? = null
    private val binding: FragmentRollBinding
        get() = _binding ?: throw RuntimeException(getString(R.string.fragmentrollbinding_null))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = FragmentRollBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDiceRecyclerView()
        observeViewModel()
        setUpClickListeners()
        setUpChangedListener()
        setupRollDiceClickListener()
        setupNavClickListener()
        getResultFromDialog()
    }

    private fun getResultFromDialog() {
        findNavController().getCurrentBackStackEntryLiveData(
            viewLifecycleOwner,
            CubesFragment.SHOULD_SHOW_BUTTON
        ) {
            if (it) viewModel.applyResult()
        }
    }

    private fun setupNavClickListener() {
        binding.historyButton.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }
        binding.cubesButton.setOnClickListener {
            findNavController().navigate(R.id.action_rollFragment_to_cubesFragment)
        }
    }

    private fun setupRollDiceClickListener() {
        binding.itemDice.setListener {
            viewModel.rollTheDice()
        }
    }

    private fun setUpChangedListener() {
        binding.rollsEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputName()
                viewModel.setNumberOfRolls(value.toString())

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

    private fun setUpClickListeners() {
        binding.incrementButton.setOnClickListener {
            viewModel.incrementNumberOfRolls()
        }
        binding.decrementButton.setOnClickListener {
            viewModel.decrementNumberOfRolls()
        }
    }

    private fun observeViewModel() {
        viewModel.errorInput.observe(viewLifecycleOwner) { showError ->
            binding.rollsEtLayout.error = if (showError) getString(R.string.invalid_input) else null
        }

        viewModel.diceList.observe(viewLifecycleOwner) { diceList ->
            diceAdapter.submitList(diceList)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            selectDice(state.dice.imageEmpty, state.dice.faces)

            val editTextValue = binding.rollsEt.text.toString().toIntOrNull()
            if (editTextValue != null && editTextValue != state.numberOfRolls) {
                binding.rollsEt.setText(state.numberOfRolls.toString())
            }

            binding.itemDice.setDiceText(state.rollingResult.toString())
            binding.attentionTextView.isVisible = state.shouldShowAttention
        }
    }

    private fun selectDice(image: Int, faces: Int) {
        with(binding.itemDice) {
            setDiceImage(image)
            when (faces) {
                4 -> {
                    setDicePadding(
                        0,
                        PADDING_DICE_4_TOP,
                        PADDING_DICE_4_RIGHT,
                        PADDING_DICE_4_BOTTOM
                    )
                    setDiceTextSize(TEXT_SIZE_DICE_4)
                }
                6 -> {
                    setDicePadding(
                        0,
                        PADDING_DICE_6_TOP,
                        PADDING_DICE_6_RIGHT,
                        PADDING_DICE_6_BOTTOM
                    )
                    setDiceTextSize(TEXT_SIZE_DICE_6)
                }
                8 -> {
                    setDicePadding(
                        DEFAULT_PADDING,
                        PADDING_DICE_8_TOP,
                        PADDING_DICE_8_RIGHT,
                        PADDING_DICE_8_BOTTOM
                    )
                    setDiceTextSize(TEXT_SIZE_DICE_8)
                }
                10 -> {
                    setDicePadding(
                        DEFAULT_PADDING,
                        PADDING_DICE_10_TOP,
                        PADDING_DICE_10_RIGHT,
                        PADDING_DICE_10_BOTTOM
                    )
                    setDiceTextSize(TEXT_SIZE_DICE_10)
                }
                12 -> {
                    setDicePadding(
                        DEFAULT_PADDING,
                        PADDING_DICE_12_TOP,
                        PADDING_DICE_12_RIGHT,
                        PADDING_DICE_12_BOTTOM
                    )
                    setDiceTextSize(TEXT_SIZE_DICE_12)
                }
                20 -> {
                    setDicePadding(
                        PADDING_DICE_20_LEFT,
                        PADDING_DICE_20_TOP,
                        PADDING_DICE_20_RIGHT,
                        PADDING_DICE_20_BOTTOM
                    )
                    setDiceTextSize(TEXT_SIZE_DICE_20)
                }
                else -> {
                    setDicePadding(
                        DEFAULT_PADDING,
                        DEFAULT_PADDING,
                        DEFAULT_PADDING,
                        DEFAULT_PADDING
                    )
                    setDiceTextSize(DEFAULT_TEXT_SIZE)
                }
            }
        }
    }

    private fun setupDiceRecyclerView() {
        with(binding.diceListRv) {
            diceAdapter = DiceAdapter()
            adapter = diceAdapter
            setUpClickListener()
            setupSwipeListener(binding.diceListRv)
        }
    }

    private fun setupSwipeListener(dicesRecyclerViewList: RecyclerView) {
        val callback = object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.DOWN or ItemTouchHelper.UP
        ) {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dice = diceAdapter.currentList[viewHolder.adapterPosition]
                val swipeFlags = if (dice.isNotDefault()) {
                    ItemTouchHelper.DOWN or ItemTouchHelper.UP
                } else {
                    0
                }
                return makeMovementFlags(0, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val dice = diceAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteDice(dice)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(dicesRecyclerViewList)
    }

    private fun setUpClickListener() {
        diceAdapter.onItemClickListener = { dice ->
        if (dice.faces == ADD_CUSTOM_DICE) {
                navigateToAddFragment()
            } else {
                viewModel.selectDice(dice)
            }
        }
    }

    private fun navigateToAddFragment() = findNavController().navigate(R.id.action_rollFragment_to_addFragment)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val ADD_CUSTOM_DICE = 0

        private const val PADDING_DICE_4_TOP = 135
        private const val PADDING_DICE_4_RIGHT = 10
        private const val PADDING_DICE_4_BOTTOM = 0
        private const val TEXT_SIZE_DICE_4 = 45F

        private const val PADDING_DICE_6_TOP = 45
        private const val PADDING_DICE_6_RIGHT = 20
        private const val PADDING_DICE_6_BOTTOM = 0
        private const val TEXT_SIZE_DICE_6 = 70F

        private const val PADDING_DICE_8_TOP = 20
        private const val PADDING_DICE_8_RIGHT = 5
        private const val PADDING_DICE_8_BOTTOM = 20
        private const val TEXT_SIZE_DICE_8 = 60F

        private const val PADDING_DICE_10_TOP = 0
        private const val PADDING_DICE_10_RIGHT = 5
        private const val PADDING_DICE_10_BOTTOM = 45
        private const val TEXT_SIZE_DICE_10 = 50F

        private const val PADDING_DICE_12_TOP = 10
        private const val PADDING_DICE_12_RIGHT = 2
        private const val PADDING_DICE_12_BOTTOM = 0
        private const val TEXT_SIZE_DICE_12 = 45F

        private const val PADDING_DICE_20_LEFT = 5
        private const val PADDING_DICE_20_TOP = 35
        private const val PADDING_DICE_20_RIGHT = 0
        private const val PADDING_DICE_20_BOTTOM = 15
        private const val TEXT_SIZE_DICE_20 = 50F

        private const val DEFAULT_TEXT_SIZE = 50F
        private const val DEFAULT_PADDING = 0
    }
}