package com.iddevops.sample.todo.presentation

import com.iddevops.sample.todo.presentation.fragment.home.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel{ TodoListViewModel(get()) }
}