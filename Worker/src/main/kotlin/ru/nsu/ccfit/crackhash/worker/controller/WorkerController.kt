package ru.nsu.ccfit.crackhash.worker.controller

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.ccfit.crackhash.worker.model.dto.WorkerTaskDto
import ru.nsu.ccfit.crackhash.worker.service.WorkerService

@Component
class WorkerController(private val workerService: WorkerService) {
    @RabbitListener(queues = ["manager-to-worker"])
    fun takeTask(crackRequest: WorkerTaskDto) {
        workerService.takeTask(crackRequest)
    }
}