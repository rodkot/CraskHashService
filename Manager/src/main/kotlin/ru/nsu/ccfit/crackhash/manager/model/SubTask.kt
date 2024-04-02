package ru.nsu.ccfit.crackhash.manager.model

import java.time.LocalDateTime

data class SubTask(
    val part: Int,
    var status: Status,
    //val result: List<String>,
    val sendTime: LocalDateTime
){
    enum class Status {
        PENDING,
        COMPLETED,
        TIMEOUT,
        ERROR
    }
}