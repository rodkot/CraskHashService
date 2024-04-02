package ru.nsu.ccfit.crackhash.manager.model.dto

data class WorkerTaskDto (
    val hash: String,
    val maxLength: Int,
    val partNumber: Int,
    val partCount: Int
)