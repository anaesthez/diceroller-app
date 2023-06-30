package ru.nesterov.diceroller.app.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nesterov.diceroller.app.data.history.HistoryDao
import ru.nesterov.diceroller.app.data.history.entities.HistoryElementDbEntity

@Database(
    version = 1,
    entities = [
        HistoryElementDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getHistoryDao(): HistoryDao
}