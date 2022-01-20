package com.iddevops.sample.todo.data.activity.web

import com.iddevops.sample.todo.data.activity.model.TodoTempData
import retrofit2.http.GET
import retrofit2.http.Query

interface TodoListApi {
    @GET("/todos")
    suspend fun getTodos(
        @Query("userId") userId: String
    ): List<TodoTempData>
}