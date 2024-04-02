package ru.nsu.ccfit.crackhash.manager.repository

import org.springframework.stereotype.Repository
import ru.nsu.ccfit.crackhash.manager.model.SubTask
import ru.nsu.ccfit.crackhash.manager.model.Task

@Repository
interface TaskRepository {
    fun add(hash: String, maxLen: Int): String
    fun get(id: String): Task
    fun merge(taskId: String, merge: (Task) -> Task)
    fun foreach(function: (String, Task) -> Unit)
    fun foreachMerge(merge: (String, Task) -> Task)
}