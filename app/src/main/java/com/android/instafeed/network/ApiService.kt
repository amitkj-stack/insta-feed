package com.android.instafeed.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API endpoint: https://api.flickr.com/services/rest/
? access_key = API_KEY
 */
interface ApiService {

    @GET("?method=flickr.photos.search&nojsoncallback=1&format=json")
    fun search(
        @Query("api_key") apiKey: String,
        @Query("text") text: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Deferred<Response<ApiEndPoint>>

}