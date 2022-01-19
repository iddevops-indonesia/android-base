package com.iddevops.sample.presentation

import com.iddevops.sample.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel{ HomeViewModel(get()) }
}