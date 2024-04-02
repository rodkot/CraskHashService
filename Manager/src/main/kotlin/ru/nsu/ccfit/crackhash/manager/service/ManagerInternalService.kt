package ru.nsu.ccfit.crackhash.manager.service

import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerResponseDto

interface ManagerInternalService {
    fun crackRequest(response: WorkerResponseDto)
}