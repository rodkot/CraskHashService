package ru.nsu.ccfit.crackhash.manager.repository.impl

import org.springframework.stereotype.Repository
import ru.nsu.ccfit.crackhash.manager.exception.impl.NotFountException
import ru.nsu.ccfit.crackhash.manager.model.Task
import ru.nsu.ccfit.crackhash.manager.repository.TaskRepository
import java.util.concurrent.ConcurrentHashMap

@Repository
class TaskRepositoryImpl : TaskRepository {
    val base = ConcurrentHashMap<String, Task>()

    fun taskId(hash: String) = hash

    override fun add(hash: String, maxLen: Int): String {
        base[taskId(hash)] = Task(maxLen, Task.Status.PENDING, emptyList())
        return taskId(hash)
    }

    override fun get(id: String): Task {
        if (base[id]!=null)
            return base[id]!!
        else
            throw NotFountException()
    }

    override fun merge(taskId: String, merge: (Task) -> Task) {
        base.compute(taskId) { _, old ->
            old?.let { merge.invoke(it) }
        }
    }

    override fun foreach(function: (String, Task) -> Unit) {
        base.forEach(function)
    }

    override fun foreachMerge(merge: (String, Task) -> Task) {
        base.forEach{
            base.compute(it.key){ key,value ->
                merge.invoke(key, value!!)
            }
        }
    }

}