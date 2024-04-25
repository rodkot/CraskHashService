package ru.nsu.ccfit.crackhash.manager.controller

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerResponseDto
import ru.nsu.ccfit.crackhash.manager.service.ManagerInternalService

@Component
class ManagerInternalController(val managerInternalService: ManagerInternalService) {
    @RabbitListener(queues = ["worker-to-manager"])
    fun crackRequest(response: WorkerResponseDto) = managerInternalService.crackRequest(response)
}