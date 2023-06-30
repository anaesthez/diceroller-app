package ru.nesterov.diceroller.app.data.history.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nesterov.diceroller.app.domain.entity.HistoryResult

@Entity(
    tableName = "history"
)
data class HistoryElementDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val image: Int,
    val rolls: Int,
    val result: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {

    fun toHistoryResult(): HistoryResult = HistoryResult(
        id = id,
        image = image,
        rolls = rolls,
        result = result,
        createdAt = createdAt
    )

    companion object {
        fun fromHistoryResult(historyResult: HistoryResult): HistoryElementDbEntity =
            HistoryElementDbEntity (
                id = historyResult.id,
                image = historyResult.image,
                rolls = historyResult.rolls,
                result = historyResult.result,
                createdAt = historyResult.createdAt
            )
    }
}
