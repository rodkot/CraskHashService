package ru.nsu.ccfit.crackhash.manager.worker.impl

import org.slf4j.Logger
import org.springframework.amqp.AmqpException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.stereotype.Service
import ru.nsu.ccfit.crackhash.manager.model.TaskMongoEntity
import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerTaskDto
import ru.nsu.ccfit.crackhash.manager.repository.MongoTaskRepository
import ru.nsu.ccfit.crackhash.manager.worker.WorkerApi

@Service
class WorkerApiImpl(
    private val logger: Logger,
    private val rabbit: RabbitTemplate,
    private val mongoTaskRepository: MongoTaskRepository
) : WorkerApi {
    override fun takeTask(task: TaskMongoEntity, partNumber: Int, retryCount: Int) {
        try {
            logger.info("Sending [$partNumber|${task.partCount}] ${task.requestId}")
            mongoTaskRepository.save(task.apply { sendSet = sendSet.filterNot { it == partNumber }.toSet() })
            rabbit.convertAndSend(
                "manager-to-worker",
                "worker",
                WorkerTaskDto(task.hash, task.maxLength, partNumber, task.partCount)
            )
            logger.info("Sent [$partNumber|${task.partCount}] ${task.requestId}")
        } catch (e: AmqpException) {
            mongoTaskRepository.save(task.apply { sendSet = sendSet + partNumber })
            logger.error(
                """
                    Got AmqpException ${e::class.simpleName}.
                    Task will be saved in data base
                """
            )
        } catch (e: OptimisticLockingFailureException) {
            if (retryCount > 1) {
                logger.info("Sent [$partNumber|${task.partCount}] ${task.requestId} RETRY $retryCount")
                takeTask(task, partNumber, retryCount - 1)
            } else {
                logger.info("Sent [$partNumber|${task.partCount}] ${task.requestId} FAILED")
            }
        }
    }
}