package ru.nsu.ccfit.crackhash.manager.repository.impl

import org.springframework.stereotype.Repository
import ru.nsu.ccfit.crackhash.manager.model.StatusTask
import ru.nsu.ccfit.crackhash.manager.model.SubTask
import ru.nsu.ccfit.crackhash.manager.repository.SubTaskRepository
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Repository
class SubTaskRepositoryImpl : SubTaskRepository {
    val baseSubTask = ConcurrentHashMap<String, SubTask>()

    val baseStatusTask = ConcurrentHashMap<String, StatusTask>()
    fun subTaskId(taskId: String, part: Int) = "$taskId#$part"
    override fun add(taskId: String, part: Int) {

        baseSubTask[subTaskId(taskId, part)] = SubTask(part, SubTask.Status.PENDING, LocalDateTime.now())
        baseStatusTask[taskId] = StatusTask()
    }

    override fun get(taskId: String, part: Int): SubTask? {
        return baseSubTask[subTaskId(taskId, part)]
    }

    override fun merge(taskId: String, part: Int, merge: (String, SubTask?) -> SubTask?) {
        baseSubTask.compute(subTaskId(taskId, part)) { id, oldValue ->
            merge.invoke(id, oldValue)
        }
    }

    override fun getStatus(taskId: String): StatusTask? {
        return baseStatusTask[taskId]
    }

//    override fun addResult(taskId: String, part: Int, result: List<String>) {
//        if (baseSubTask[subTaskId(taskId, part)] != null) {
//
//            baseSubTask.compute(subTaskId(taskId, part)) { _, oldValue ->
//                oldValue?.copy(status = SubTask.Status.COMPLETED, result = result)
//            }
//
//            baseStatusTask.compute(taskId) { _, oldValue ->
//                if (oldValue != null) {
//                    oldValue.ready++;
//                }
//                oldValue
//            }
//        }
//    }

//    override fun updateStatus(taskId: String, part: Int, status: SubTask.Status) {
//        if (baseSubTask.containsKey(subTaskId(taskId, part))) {
//
//            baseSubTask.compute(subTaskId(taskId, part)) { _, oldValue ->
//                oldValue?.copy(status = status)
//            }
//
//            baseStatusTask.compute(taskId) { _, oldValue ->
//                if (oldValue != null) {
//                    when (status) {
//                        SubTask.Status.TIMEOUT -> oldValue.timeOut++
//                        SubTask.Status.ERROR -> oldValue.error++
//                        else -> {}
//                    }
//                }
//                oldValue
//            }
//        }
//    }


    override fun foreach(function: (String, SubTask) -> Unit) {
        baseSubTask.forEach {
            function(it.key, it.value)
        }
    }

//    override fun mergeAllByCondition(condition: (SubTask) -> Boolean, merge: (String, SubTask) -> SubTask) {
//        baseSubTask.replaceAll { subtaskIs, value ->
//            if (condition(value)) merge(subtaskIs, value) else value
//        }
//    }
}