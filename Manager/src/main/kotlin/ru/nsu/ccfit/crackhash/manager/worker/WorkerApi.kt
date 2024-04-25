package ru.nsu.ccfit.crackhash.manager.worker

import retrofit2.http.Body
import retrofit2.http.POST
import ru.nsu.ccfit.crackhash.manager.model.TaskMongoEntity
import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerTaskDto

interface WorkerApi {
    fun takeTask(task: TaskMongoEntity, partNumber: Int, retryCount: Int = 5)
}