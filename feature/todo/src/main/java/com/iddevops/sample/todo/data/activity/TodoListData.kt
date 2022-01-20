package com.iddevops.sample.todo.data.activity

import com.iddevops.data.DataRequest
import com.iddevops.sample.todo.data.activity.web.TodoListApi
import com.iddevops.sample.todo.domain.model.TodoData
import com.iddevops.sample.todo.domain.repo.TodoListRepository

class TodoListData(private val web: TodoListApi) : TodoListRepository {
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