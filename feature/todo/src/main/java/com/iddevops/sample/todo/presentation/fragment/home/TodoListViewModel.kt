package com.iddevops.sample.todo.presentation.fragment.home

import com.iddevops.presentation.viewmodel.BaseViewModel
import com.iddevops.sample.todo.domain.model.TodoData
import com.iddevops.sample.todo.domain.usecase.TodoListUseCase

class TodoListViewModel(
    private val todoListUseCase: TodoListUseCase
) : BaseViewModel() {

    val todos = DataOperation<List<TodoData>>()
    fun getTodos(userId: String) = todos.call{
        todoListUseCase.getTodos(userId)
    }

}