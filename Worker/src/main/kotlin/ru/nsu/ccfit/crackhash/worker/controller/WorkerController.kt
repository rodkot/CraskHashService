package ru.nsu.ccfit.crackhash.worker.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.ccfit.crackhash.worker.model.dto.WorkerTaskDto
import ru.nsu.ccfit.crackhash.worker.service.WorkerService

@RestController
@RequestMapping("/internal/api/worker/hash/crack/task")
class WorkerController(private val workerService: WorkerService) {
    @PostMapping
    fun takeTask(@RequestBody crackRequest: WorkerTaskDto) {
        workerService.takeTask(crackRequest)
    }
}