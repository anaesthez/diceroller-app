package ru.nesterov.diceroller.app.domain.entity

data class HistoryResult(
    val id: Int,
    val createdAt: Long,
    val image: Int,
    val rolls: Int,
    val result: Int
)