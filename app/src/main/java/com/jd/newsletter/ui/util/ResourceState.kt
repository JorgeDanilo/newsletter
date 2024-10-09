package com.jd.newsletter.ui.util

sealed class ResourceState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) : ResourceState<T>(data)
    class Error<T>(message: String? = null, data: T? = null) : ResourceState<T>(data, message)
    class Loading<T> : ResourceState<T>()
    class Empty<T> : ResourceState<T>()
}