package ru.nsu.ccfit.crackhash.manager.model.dto

data class WorkerResponseDto(
    val partNumber: Int,
    val requestId: String,
    val value: List<String>?
)
