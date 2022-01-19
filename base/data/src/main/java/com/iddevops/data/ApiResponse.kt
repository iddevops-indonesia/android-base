package com.iddevops.data

import com.iddevops.utils.worker.runInMainThread
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun <T : Any> DevData() = MutableStateFlow(DataRequest<T>()).default()

fun <T : Any> MutableStateFlow<DataRequest<T>>.default() = runInMainThread {
    value = DataRequest.Default()
    this
}

fun <T : Any> MutableStateFlow<DataRequest<T>>.loading() = runInMainThread {
    value = DataRequest.Loading()
    this
}

fun <T : Any> MutableStateFlow<DataRequest<T>>.empty() = runInMainThread {
    value = DataRequest.Empty()
    this
}

fun <T : Any> MutableStateFlow<DataRequest<T>>.success(data: T) = runInMainThread {
    value = DataRequest.Success(data)
    this
}

fun <T : Any> MutableStateFlow<DataRequest<T>>.failure(message: String) = runInMainThread {
    value = DataRequest.Failure(message)
    this
}

val <T : Any> MutableStateFlow<DataRequest<T>>.asImmutable: StateFlow<DataRequest<T>>
    get() = this

open class DataRequest<T : Any> {
    class Default<T : Any> : DataRequest<T>()

    class Loading<T : Any> : DataRequest<T>()

    class Empty<T : Any> : DataRequest<T>()

    class Success<T : Any>(private var _data: T?) : DataRequest<T>() {
        val data: T? get() = _data
    }

    class Failure<T : Any>(private var _message: String) : DataRequest<T>() {
        val message: String get() = _message
    }
}

interface DataObserver<T : Any> {
    fun onRequestDefault() {}
    fun onRequestLoading() {}
    fun onRequestSuccess(data: T?) {}
    fun onRequestEmpty() {}
    fun onRequestFailure(message: String) {}
}