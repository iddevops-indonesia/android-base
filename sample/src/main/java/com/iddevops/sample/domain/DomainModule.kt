package com.iddevops.sample.domain

import com.iddevops.sample.domain.repo.ActivityRepository
import com.iddevops.sample.domain.usecase.ActivityUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<ActivityUseCase>{ get<ActivityRepository>() }
}