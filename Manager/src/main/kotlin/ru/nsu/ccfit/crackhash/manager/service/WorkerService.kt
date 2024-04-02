package ru.nsu.ccfit.crackhash.manager.service

import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerTaskDto

interface WorkerService {
    fun send(requestDto: WorkerTaskDto)
}