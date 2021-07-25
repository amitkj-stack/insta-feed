package com.android.instafeed.data

import com.android.instafeed.network.ApiEndPoint

sealed class Resource {
    class Success(val response: ApiEndPoint?) : Resource()
    class Error(val message: String?) : Resource()
}