package ru.nsu.ccfit.crackhash.manager.model

import ru.nsu.ccfit.crackhash.manager.model.dto.StatusDto

data class Task(
    val maxLength: Int,
    val status: Status,
    val result: List<String>
){
    enum class Status {
        PENDING,
        COMPLETED,
        TIMEOUT,
        ERROR
    }
}