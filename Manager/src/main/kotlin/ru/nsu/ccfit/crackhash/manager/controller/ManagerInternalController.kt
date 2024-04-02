package ru.nsu.ccfit.crackhash.manager.controller

import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerResponseDto
import ru.nsu.ccfit.crackhash.manager.service.ManagerInternalService

@RestController
@RequestMapping("/internal/api/manager/hash/crack/request")
class ManagerInternalController(val managerInternalService: ManagerInternalService) {
    @PatchMapping
    fun crackRequest(@RequestBody response: WorkerResponseDto) = managerInternalService.crackRequest(response)
}