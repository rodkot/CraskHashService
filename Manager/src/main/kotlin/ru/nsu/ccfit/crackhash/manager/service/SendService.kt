package ru.nsu.ccfit.crackhash.manager.service

import ru.nsu.ccfit.crackhash.manager.model.TaskMongoEntity


interface SendService {
    fun init()
    fun execute(requestId: String)
    fun sendAfterRabbitReconnect(findAllByTaskStatus: List<TaskMongoEntity>)
}