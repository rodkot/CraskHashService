package ru.nsu.ccfit.crackhash.worker.service

import ru.nsu.ccfit.crackhash.worker.model.dto.WorkerTaskDto

interface WorkerService {
    fun takeTask(crackRequest: WorkerTaskDto)
}