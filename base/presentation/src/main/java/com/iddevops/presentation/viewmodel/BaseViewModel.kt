package com.iddevops.presentation.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iddevops.data.DataObserver
import com.iddevops.data.DataRequest
import com.iddevops.data.DevData
import com.iddevops.data.loading
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect

@WorkerThread
open class BaseViewModel : ViewModel() {

    inner class DataOperation<T : Any> {

        init {
            // cancel job on viewModel cleared
            this@BaseViewModel.addOnClearedAction { job?.cancel() }
        }

        private var job: Job? = null
        private val data = DevData<T>()

        fun call(requester: suspend () -> DataRequest<T>) {
            job?.cancel()
            data.loading()
                .buffer()
            job = this@BaseViewModel.viewModelScope.launch {
                data.emit(requester.invoke())
            }
        }


        suspend fun observe(observer: DataObserver<T>) {
            data.collect{
                when (it) {
                    is DataRequest.Default -> {
                        observer.onRequestDefault()
                    }
                    is DataRequest.Loading -> {
                        observer.onRequestLoading()
                    }
                    is DataRequest.Success -> {
                        observer.onRequestSuccess(it.data)
                    }
                    is DataRequest.Empty -> {
                        observer.onRequestEmpty()
                    }
                    is DataRequest.Failure -> {
                        observer.onRequestFailure(it.message)
                    }
                }
            }
        }
    }

    private val onClearedAction: ArrayList<()->Unit> = arrayListOf()
    fun addOnClearedAction(task: ()->Unit) = onClearedAction.add(task)

    override fun onCleared() {
        onClearedAction.forEach {
            it.invoke()
        }
        super.onCleared()
    }
}