package com.iddevops.sample.todo.domain.usecase

import com.iddevops.data.DataRequest
import com.iddevops.sample.todo.domain.model.TodoData

interface TodoListUseCase {
    suspend fun getTodos(userId: String): DataRequest<List<TodoData>>
}