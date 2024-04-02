package ru.nsu.ccfit.crackhash.worker.service

import ru.nsu.ccfit.crackhash.worker.model.enity.WorkerTask

interface TaskExecutorService {
    fun takeNewTask(workerTask: WorkerTask)
}