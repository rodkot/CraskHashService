package ru.nsu.ccfit.crackhash.manager.scheduler.impl

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.nsu.ccfit.crackhash.manager.model.SubTask
import ru.nsu.ccfit.crackhash.manager.model.Task
import ru.nsu.ccfit.crackhash.manager.model.dto.StatusDto
import ru.nsu.ccfit.crackhash.manager.repository.SubTaskRepository
import ru.nsu.ccfit.crackhash.manager.repository.TaskRepository
import ru.nsu.ccfit.crackhash.manager.scheduler.TaskCheckerScheduler
import ru.nsu.ccfit.crackhash.manager.scheduler.TimeoutScheduler

@Service
@EnableScheduling
class TaskCheckerSchedulerImpl(
    @Value("\${part.count}")
    private val partCount: Int,
    private val logger: Logger,
    private val subTaskRepository: SubTaskRepository,
    private val taskRepository: TaskRepository
) : TaskCheckerScheduler {
    @Scheduled(cron = "0/10 * * * * ?")
    override fun enable() {
        logger.info("The check Task has started!!!")

        taskRepository.foreachMerge { hash, task ->
            if (task.status == Task.Status.PENDING) {
                for (part in 1..partCount) {
                    val subTask = subTaskRepository.get(hash, part)

                    if (subTask?.status == SubTask.Status.PENDING){
                        return@foreachMerge task
                    }

                    if (subTask?.status == SubTask.Status.TIMEOUT){
                        logger.info("Task #${hash} TimeOut")
                        return@foreachMerge task.copy(status = Task.Status.TIMEOUT)
                    }

                    if (subTask?.status == SubTask.Status.ERROR){
                        logger.info("Task #${hash} Error")
                        return@foreachMerge task.copy(status = Task.Status.ERROR)
                    }

                    logger.info("Task #${hash} COMPLETED")
                    return@foreachMerge task.copy(status = Task.Status.COMPLETED)
                }
            }
            task
        }
    }
}