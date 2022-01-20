package com.iddevops.sample.todo.domain

import com.iddevops.sample.todo.domain.repo.TodoListRepository
import com.iddevops.sample.todo.domain.usecase.TodoListUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<TodoListUseCase>{ get<TodoListRepository>() }
}