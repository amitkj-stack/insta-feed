package com.android.instafeed.data

import android.content.Context
import com.android.instafeed.BuildConfig
import com.android.instafeed.R
import com.android.instafeed.network.ApiEndPoint
import com.android.instafeed.network.ApiService
import org.json.JSONObject
import retrofit2.Response
import java.net.SocketTimeoutException


class PhotoRepositoryImpl constructor(
    private val context: Context,
    private val apiService: ApiService,
    private val appPrefs: AppPrefs
) : PhotoRepository {

    override val lastSearchedKeyword: String
        get() = appPrefs.lastSearchedKeyword


    override suspend fun search(query: String, perPage: Int, page: Int): Resource {
        val retrofitResponse: Response<ApiEndPoint>
        try {
            retrofitResponse = apiService.search(BuildConfig.API_KEY, query, perPage, page).await()
        } catch (e: Exception) {
            return if (e is SocketTimeoutException) Resource.Error(context.getString(R.string.err_time_out)) else Resource.Error(
                e.message
            )
        }
        return if (retrofitResponse.isSuccessful) {
            val response = retrofitResponse.body()
            if (response?.stat == "ok") {
                appPrefs.lastSearchedKeyword = query
                Resource.Success(response)
            }else
                Resource.Error(parseError(retrofitResponse))
        } else {
            // Retrofit call executed but response wasn't in the 200s
            Resource.Error(parseError(retrofitResponse))
        }

    }

    private fun parseError(response: Response<ApiEndPoint>): String? {
        response.errorBody()?.let { responseBody ->
            return JSONObject(responseBody.string()).optJSONObject("error")?.optString("message")
        }
        return null
    }

}