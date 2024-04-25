package ru.nsu.ccfit.crackhash.manager.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import ru.nsu.ccfit.crackhash.manager.model.TaskMongoEntity
import ru.nsu.ccfit.crackhash.manager.model.TaskStatus

@Repository
interface MongoTaskRepository : MongoRepository<TaskMongoEntity, String> {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun findFirstByRequestId(requestId: String): TaskMongoEntity

    fun findAllByTaskStatus(status: TaskStatus): List<TaskMongoEntity>
}