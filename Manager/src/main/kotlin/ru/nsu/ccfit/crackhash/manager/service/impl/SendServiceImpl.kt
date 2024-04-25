package ru.nsu.ccfit.crackhash.manager.service.impl

import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.nsu.ccfit.crackhash.manager.model.TaskMongoEntity
import ru.nsu.ccfit.crackhash.manager.model.TaskStatus
import ru.nsu.ccfit.crackhash.manager.repository.MongoTaskRepository
import ru.nsu.ccfit.crackhash.manager.service.SendService
import ru.nsu.ccfit.crackhash.manager.worker.WorkerApi

@Service
class SendServiceImpl(
    @Value("\${subtask.timeout}")
    private val timeout: Int,
    private val logger: Logger,
    private val workerApi: WorkerApi,
    private val mongoTaskRepository: MongoTaskRepository,
    private val rabbit: RabbitTemplate,
) : SendService {
    @PostConstruct
    override fun init() {
        rabbit.connectionFactory.addConnectionListener {
            logger.info("Rabbit reconnect")
            sendAfterRabbitReconnect(mongoTaskRepository.findAllByTaskStatus(TaskStatus.IN_PROGRESS))
        }
    }

    override fun execute(requestId: String) {
        val task = mongoTaskRepository.findFirstByRequestId(requestId)

        (1..task.partCount).forEach { partNumber ->
            workerApi.takeTask(task, partNumber)
        }
    }

    override fun sendAfterRabbitReconnect(findAllByTaskStatus: List<TaskMongoEntity>) {
        findAllByTaskStatus.forEach { task ->
            when {
                task.isTimeout(timeout) -> mongoTaskRepository.save(task.apply { taskStatus = TaskStatus.ERROR })
                task.isToResend() -> task.sendSet.forEach { partNumber -> workerApi.takeTask(task, partNumber) }
            }
        }
    }
}