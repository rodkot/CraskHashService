package ru.nsu.ccfit.crackhash.manager.worker

import retrofit2.http.Body
import retrofit2.http.POST
import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerTaskDto

interface WorkerApi {
    @POST("worker/hash/crack/task")
    suspend fun takeTask(@Body crackRequest: WorkerTaskDto)
}