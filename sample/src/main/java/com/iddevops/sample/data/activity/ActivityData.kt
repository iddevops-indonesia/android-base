package com.iddevops.sample.data.activity

import com.iddevops.data.DataRequest
import com.iddevops.sample.data.activity.web.ActivityApi
import com.iddevops.sample.domain.model.TodoData
import com.iddevops.sample.domain.repo.ActivityRepository

class ActivityData(private val web: ActivityApi) : ActivityRepository {
    override suspend fun getTodos(userId: String): DataRequest<List<TodoData>> {
        kotlin.runCatching {
            web.getTodos(userId)
        }.onFailure {
            return DataRequest.Failure(it.message ?: "unknown error")
        }.onSuccess {
            return if (it.isNotEmpty()) DataRequest.Success(
                it.map { todo ->
                    TodoData(
                        id = todo.id ?: "",
                        userId = todo.userId ?: "",
                        title = todo.title ?: "",
                        isComplete = todo.isComplete ?: false
                    )
                }
            ) else DataRequest.Empty()
        }

        throw Error("unhandled error")
    }
}