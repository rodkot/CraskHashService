package ru.nsu.ccfit.crackhash.manager.model.dto

data class CrackRequestDto (
    val hash: String,
    val maxLength: Int
)