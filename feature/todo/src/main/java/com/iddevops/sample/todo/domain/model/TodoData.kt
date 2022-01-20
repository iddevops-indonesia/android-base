package com.iddevops.sample.todo.domain.model

data class TodoData(
    val userId: String,
    val id: String,
    val title: String,
    val isComplete: Boolean
)