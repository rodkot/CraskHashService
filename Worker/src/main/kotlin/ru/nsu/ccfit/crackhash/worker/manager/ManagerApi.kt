package ru.nsu.ccfit.crackhash.worker.manager

import retrofit2.http.Body
import retrofit2.http.PATCH
import ru.nsu.ccfit.crackhash.worker.model.dto.WorkerResponseDto

interface ManagerApi {
    @PATCH("manager/hash/crack/request")
    suspend fun sendTaskResult(@Body result: WorkerResponseDto)
}