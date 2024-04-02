package ru.nsu.ccfit.crackhash.manager.service.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.nsu.ccfit.crackhash.manager.model.SubTask
import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerResponseDto
import ru.nsu.ccfit.crackhash.manager.repository.SubTaskRepository
import ru.nsu.ccfit.crackhash.manager.repository.TaskRepository
import ru.nsu.ccfit.crackhash.manager.service.ManagerInternalService

@Service
class ManagerInternalServiceImpl(
    @Value("\${workers.count}")
    private val partCount: Int,
    private val logger: Logger,
    private val taskRepository: TaskRepository,
    private val subTaskRepository: SubTaskRepository
) : ManagerInternalService {
    override fun crackRequest(response: WorkerResponseDto) {
        logger.info("Response received ${response.requestId} part #${response.partNumber} of $partCount with result: ${response.value}")

        CoroutineScope(Dispatchers.Default).launch {

            if (response.value != null) {
                taskRepository.merge(response.requestId) { it.copy(result = it.result + response.value) }
                subTaskRepository.merge(response.requestId, response.partNumber) { _, subtask ->
                    subtask?.copy(status = SubTask.Status.COMPLETED)
                }
            } else {
                subTaskRepository.merge(response.requestId, response.partNumber) { _, subtask ->
                    subtask?.copy(status = SubTask.Status.ERROR)
                }
            }
        }
    }
}