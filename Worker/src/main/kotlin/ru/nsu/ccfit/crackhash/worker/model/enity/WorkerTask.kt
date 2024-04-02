package ru.nsu.ccfit.crackhash.worker.model.enity

import ru.nsu.ccfit.crackhash.worker.model.dto.WorkerTaskDto

data class WorkerTask (
    val hash: String,
    val maxLength: Int,
    val partNumber: Int,
    val partCount: Int
) {
    constructor(crackRequest: WorkerTaskDto) : this(
        crackRequest.hash,
        crackRequest.maxLength,
        crackRequest.partNumber,
        crackRequest.partCount
    )
}