package ru.nsu.ccfit.crackhash.manager.model.dto

data class StatusResponseDto (
    val status: StatusDto,
    val data: List<String>? = null
)