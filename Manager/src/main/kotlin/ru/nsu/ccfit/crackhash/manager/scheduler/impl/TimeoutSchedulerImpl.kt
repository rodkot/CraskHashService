package ru.nsu.ccfit.crackhash.manager.scheduler.impl

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.nsu.ccfit.crackhash.manager.model.SubTask
import ru.nsu.ccfit.crackhash.manager.repository.SubTaskRepository
import ru.nsu.ccfit.crackhash.manager.scheduler.TimeoutScheduler
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs

@Service
@EnableScheduling
class TimeoutSchedulerImpl(
    private val logger: Logger,
    @Value("\${workers.timeout}")
    private val timeOut: Int,
    private val subTaskRepository: SubTaskRepository
) : TimeoutScheduler {

    @Scheduled(cron = "0/10 * * * * ?")
    override fun enable() {
        logger.info("The timeout SubTask check has started!!!")
        subTaskRepository.foreach { id, subtask ->

            val duration = Duration.between(subtask.sendTime, LocalDateTime.now())

            if (subtask.status == SubTask.Status.PENDING && abs(duration.toMinutes()) >= timeOut) {
                logger.info("Timeout Subtask #${id}")
                subTaskRepository.merge(id, subtask.part) { _, subTask ->
                    subTask?.copy(status = SubTask.Status.TIMEOUT)
                }
            }
        }
    }
}