package ru.nsu.ccfit.crackhash.manager.model.dto

data class StatusResponseDto (
    val statusDto: StatusDto,
    val data: List<String>? = null
)