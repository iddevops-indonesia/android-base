package com.iddevops.sample.presentation.home

import com.iddevops.presentation.viewmodel.BaseViewModel
import com.iddevops.sample.domain.model.TodoData
import com.iddevops.sample.domain.usecase.ActivityUseCase

class HomeViewModel(
    private val activityUseCase: ActivityUseCase
) : BaseViewModel() {

    val todos = DataOperation<List<TodoData>>()
    fun getTodos(userId: String) = todos.call{
        activityUseCase.getTodos(userId)
    }

}