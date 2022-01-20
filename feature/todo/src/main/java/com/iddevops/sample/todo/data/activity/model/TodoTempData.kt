package com.iddevops.sample.todo.data.activity.model

import com.google.gson.annotations.SerializedName

data class TodoTempData(
    @field:SerializedName("userId")
    val userId: String?,
    @field:SerializedName("id")
    val id: String?,
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("completed")
    val isComplete: Boolean?
)