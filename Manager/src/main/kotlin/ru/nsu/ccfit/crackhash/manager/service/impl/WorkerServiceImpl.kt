package ru.nsu.ccfit.crackhash.manager.service.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import ru.nsu.ccfit.crackhash.manager.model.dto.WorkerTaskDto
import ru.nsu.ccfit.crackhash.manager.service.WorkerService
import ru.nsu.ccfit.crackhash.manager.worker.WorkerApi

@Service
class WorkerServiceImpl(private val worker: WorkerApi) : WorkerService {
    override fun send(requestDto: WorkerTaskDto) {
        CoroutineScope(Dispatchers.Default).launch {
            worker.takeTask(requestDto)
        }
    }
}