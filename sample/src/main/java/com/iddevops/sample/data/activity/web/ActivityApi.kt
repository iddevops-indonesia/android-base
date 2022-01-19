package com.iddevops.sample.data.activity.web

import com.iddevops.sample.data.activity.model.TodoTempData
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityApi {
    @GET("/todos")
    suspend fun getTodos(
        @Query("userId") userId: String
    ): List<TodoTempData>
}