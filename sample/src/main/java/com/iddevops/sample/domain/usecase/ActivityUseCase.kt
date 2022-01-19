package com.iddevops.sample.domain.usecase

import com.iddevops.data.DataRequest
import com.iddevops.sample.domain.model.TodoData

interface ActivityUseCase {
    suspend fun getTodos(userId: String): DataRequest<List<TodoData>>
}