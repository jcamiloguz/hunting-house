package com.guzcode.huntinghouse.network

import java.lang.Exception

interface Callback<T> {
    fun onSuccess(result:T?)

    fun onFailed(exception: Exception)
}