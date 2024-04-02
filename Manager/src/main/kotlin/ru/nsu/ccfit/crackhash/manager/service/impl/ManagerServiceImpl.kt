package ru.nsu.ccfit.crackhash.manager.service.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.nsu.ccfit.crackhash.manager.model.Task
import ru.nsu.ccfit.crackhash.manager.model.dto.*
import ru.nsu.ccfit.crackhash.manager.model.dto.StatusDto.*
import ru.nsu.ccfit.crackhash.manager.repository.SubTaskRepository
import ru.nsu.ccfit.crackhash.manager.repository.TaskRepository
import ru.nsu.ccfit.crackhash.manager.service.ManagerService
import ru.nsu.ccfit.crackhash.manager.service.WorkerService

@Service
class ManagerServiceImpl(
    @Value("\${workers.count}")
    private val partCount: Int,
    private val taskRepository: TaskRepository,
    private val subTaskRepository: SubTaskRepository,
    private val workerService: WorkerService
) :
    ManagerService {
    override fun crack(crackRequest: CrackRequestDto): CrackResponseDto {
        val id = taskRepository.add(crackRequest.hash, crackRequest.maxLength)

        for (part in 1..partCount) {
            subTaskRepository.add(id, part)
            CoroutineScope(Dispatchers.IO).launch {
                workerService.send(WorkerTaskDto(crackRequest.hash, crackRequest.maxLength, part, partCount))
            }
        }
        return CrackResponseDto(id)

    }

    override fun status(requestId: String): StatusResponseDto {
        val task = taskRepository.get(requestId)

        return when (task.status) {
            Task.Status.PENDING -> StatusResponseDto(IN_PROGRESS)
            Task.Status.COMPLETED -> StatusResponseDto(READY, task.result)
            Task.Status.TIMEOUT -> StatusResponseDto(IN_PROGRESS)
            Task.Status.ERROR -> StatusResponseDto(ERROR)
        }
    }
}