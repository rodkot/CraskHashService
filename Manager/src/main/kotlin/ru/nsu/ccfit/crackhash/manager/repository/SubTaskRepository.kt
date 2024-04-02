package ru.nsu.ccfit.crackhash.manager.repository

import ru.nsu.ccfit.crackhash.manager.model.StatusTask
import ru.nsu.ccfit.crackhash.manager.model.SubTask

interface SubTaskRepository {
    fun add(taskId: String, part: Int)

    fun get(taskId: String, part: Int): SubTask?

    fun merge(taskId: String, part: Int, merge: (String, SubTask?) -> SubTask?)

    fun getStatus(taskId: String): StatusTask?

    //fun addResult(taskId: String, part: Int, result: List<String>)

    //fun updateStatus(taskId: String, part: Int, status: SubTask.Status)

    fun foreach(function: (String, SubTask) -> Unit)

//    fun mergeAllByCondition(condition: (SubTask) -> Boolean, merge: (String, SubTask) -> SubTask)
}