package ru.nesterov.diceroller.app

import android.content.Context
import androidx.room.Room
import ru.nesterov.diceroller.app.data.history.RoomHistoryRepository
import ru.nesterov.diceroller.app.data.roll.RollImpl
import ru.nesterov.diceroller.app.data.room.AppDatabase
import ru.nesterov.diceroller.app.domain.repository.Cubes
import ru.nesterov.diceroller.app.domain.repository.History
import ru.nesterov.diceroller.app.domain.repository.Roll

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .build()
    }

    val historyRepository: History by lazy {
        RoomHistoryRepository(database.getHistoryDao())
    }

    val rollRepository: Roll by lazy {
        RollImpl
    }

    val cubesRepository: Cubes by lazy {
        RollImpl
    }

    fun init(context: Context) {
        applicationContext = context
    }
}