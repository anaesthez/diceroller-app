package ru.nesterov.diceroller.app.data.roll

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nesterov.diceroller.R
import ru.nesterov.diceroller.app.domain.entity.Dice
import ru.nesterov.diceroller.app.domain.entity.DiceResult
import ru.nesterov.diceroller.app.domain.repository.Cubes
import ru.nesterov.diceroller.app.domain.repository.Roll

object RollImpl: Roll, Cubes {

    private val dicesList = sortedSetOf<Dice>({ o1, o2 ->  o1.id.compareTo(o2.id) })
    private val dicesListLiveData = MutableLiveData<List<Dice>>()

    private val cubesList = sortedSetOf<DiceResult>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private val cubesListLiveData = MutableLiveData<List<DiceResult>>()

    private var rollResult: List<DiceResult>

    private val resourcesList = listOf(
        R.drawable.dice20_empty to R.drawable.dice20,
        R.drawable.dice12_empty to R.drawable.dice12,
        R.drawable.dice10_empty to R.drawable.dice10,
        R.drawable.dice8_empty to R.drawable.dice8,
        R.drawable.dice6_empty to R.drawable.dice6,
        R.drawable.dice4_empty to R.drawable.dice4,
        0 to R.drawable.dice_add
    )

    private var diceIncrementId = 0
    private var resultIncrementId = 0

    init {
        resourcesList.forEach { (key, value) ->
            initDices(
                Dice(
                id = Dice.UNDEFINED_ID,
                imageEmpty = key,
                faces = when (value) {
                    R.drawable.dice20 -> Dice.DICE20_VALUE
                    R.drawable.dice12 -> Dice.DICE12_VALUE
                    R.drawable.dice10 -> Dice.DICE10_VALUE
                    R.drawable.dice8 -> Dice.DICE8_VALUE
                    R.drawable.dice6 -> Dice.DICE6_VALUE
                    R.drawable.dice4 -> Dice.DICE4_VALUE
                    R.drawable.dice_add -> Dice.DICE_ADD
                    else -> throw RuntimeException()
                        },
                selected = false,
                image = value
            )
            )
        }
        rollResult = emptyList()
    }

    override fun rollDice(faces: Int, numberOfRolls: Int) {
        val resultList = (1..numberOfRolls).map {
            parseCubeResult((1..faces).random(), faces)
        }
        addCubes(resultList)
        rollResult = resultList
    }

    override fun reRollDice(diceResult: DiceResult, newResult: Int, oldResult: Int) {
        val updatedList = cubesList.map {
            if (it.id == diceResult.id) {
                it.copy(result = newResult)
            } else {
                it
            }
        }

        cubesList.clear()
        cubesList.addAll(updatedList)
        rollResult = cubesList.toList()
        notifyCubesList()
    }

    override fun getRollResult(): List<DiceResult> = rollResult

    private fun initDices(dice: Dice) {
        if (dice.id == Dice.UNDEFINED_ID) {
            dice.id = diceIncrementId++
        }
        dicesList.add(dice)
        notifyDicesList()
    }

    override fun getDiceList(): LiveData<List<Dice>> =
        dicesListLiveData

    override fun selectDice(dice: Dice) {
        val oldDice = getDiceById(dice.id)
        with(dicesList) {
            remove(oldDice)
            add(dice)
            unselect(dice)
        }
        notifyDicesList()
    }

    override fun getCubes(): LiveData<List<DiceResult>> = cubesListLiveData

    override fun clearCubes() {
        cubesList.clear()
        notifyCubesList()
        resultIncrementId = 0
    }

    override fun addDice(faces: Int) {
        val dice = Dice(
            id = diceIncrementId++,
            selected = false,
            imageEmpty = R.drawable.dice_custom_empty,
            image = R.drawable.dice_custom,
            faces = faces
        )
        val lastElement = dicesList.last()
        dicesList.remove(lastElement)
        dicesList.add(dice)
        dicesList.add(lastElement.copy(id = diceIncrementId++))
        notifyDicesList()
    }

    override fun deleteDice(dice: Dice) {
        dicesList.remove(dice)
        selectDice(dicesList.first())
        notifyCubesList()
    }

    private fun addCubes(resultList: List<DiceResult>) {
        cubesList.addAll(resultList)
        notifyCubesList()
    }

    private fun parseCubeResult(result: Int, faces: Int): DiceResult {
        val dice = DiceResult(
            id = resultIncrementId,
            result = result,
            faces = faces,
            imageEmpty = getImage(faces)
        )
        resultIncrementId++
        return dice
    }

    private fun getImage(faces: Int): Int = when(faces) {
        20 -> R.drawable.dice20_empty
        12 -> R.drawable.dice12_empty
        10 -> R.drawable.dice10_empty
        8 -> R.drawable.dice8_empty
        6 -> R.drawable.dice6_empty
        4 -> R.drawable.dice4_empty
        else -> R.drawable.dice_custom_empty
    }

    private fun notifyCubesList() {
        cubesListLiveData.value = cubesList.toList()
    }

    private fun getDiceById(id: Int): Dice =
        dicesList.first { it.id == id }

    private fun unselect(dice: Dice) =
        dicesList.forEach { diceInList ->
            if (diceInList.id != dice.id) diceInList.selected = false
        }

    private fun notifyDicesList() {
        dicesListLiveData.value = dicesList.toList()
    }
}