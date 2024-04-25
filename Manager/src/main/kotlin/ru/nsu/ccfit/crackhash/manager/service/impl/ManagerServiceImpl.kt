package ru.nsu.ccfit.crackhash.manager.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.nsu.ccfit.crackhash.manager.model.TaskMongoEntity
import ru.nsu.ccfit.crackhash.manager.model.TaskStatus
import ru.nsu.ccfit.crackhash.manager.model.dto.*
import ru.nsu.ccfit.crackhash.manager.model.dto.StatusDto.*
import ru.nsu.ccfit.crackhash.manager.repository.MongoTaskRepository
import ru.nsu.ccfit.crackhash.manager.service.ManagerService
import ru.nsu.ccfit.crackhash.manager.service.SendService

@Service
class ManagerServiceImpl(
    @Value("\${part.count}")
    private val partCount: Int,
    private val sendService: SendService,
    private val mongoTaskRepository: MongoTaskRepository
) :
    ManagerService {
    override fun crack(crackRequest: CrackRequestDto): CrackResponseDto {
        val id = mongoTaskRepository.save(
            TaskMongoEntity(
                requestId = crackRequest.hash,
                hash = crackRequest.hash,
                maxLength = crackRequest.maxLength,
                partCount = partCount
            )
        )

        sendService.execute(id.requestId)

        return CrackResponseDto(id.requestId)

    }

    override fun status(requestId: String): StatusResponseDto {
        val task = mongoTaskRepository.findFirstByRequestId(requestId)

        return when (task.taskStatus) {
            TaskStatus.IN_PROGRESS -> StatusResponseDto(IN_PROGRESS)
            TaskStatus.READY -> StatusResponseDto(READY, task.resultSet.toList())
            TaskStatus.ERROR -> StatusResponseDto(ERROR)
        }
    }
}