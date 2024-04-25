package ru.nsu.ccfit.crackhash.manager.service.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.nsu.ccfit.crackhash.manager.model.TaskStatus
import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerResponseDto
import ru.nsu.ccfit.crackhash.manager.repository.MongoTaskRepository
import ru.nsu.ccfit.crackhash.manager.service.ManagerInternalService

@Service
class ManagerInternalServiceImpl(
    @Value("\${subtask.timeout}")
    private val timeout: Int,
    @Value("\${part.count}")
    private val partCount: Int,
    private val logger: Logger,
    private val taskRepository: MongoTaskRepository
) : ManagerInternalService {
    override fun crackRequest(response: WorkerResponseDto) {
        logger.info("Response received ${response.requestId} part #${response.partNumber} of $partCount with result: ${response.value}")

        taskRepository.save(
            taskRepository.findFirstByRequestId(response.requestId).apply {
                receivedTaskCounter++

                taskStatus = if (response.value == null || isTimeout(timeout)) TaskStatus.ERROR
                else {
                    resultSet = resultSet + response.value
                    if (receivedTaskCounter == partCount) TaskStatus.READY else taskStatus
                }

                logger.info("Request $requestId [${response.partNumber}|$partCount] $taskStatus")
            }
        )
    }
}