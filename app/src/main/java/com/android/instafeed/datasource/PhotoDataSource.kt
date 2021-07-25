package com.android.instafeed.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.instafeed.data.Photo
import com.android.instafeed.data.PhotoRepository
import com.android.instafeed.data.Resource
import com.android.instafeed.network.NetworkState
import kotlinx.coroutines.*

class PhotoDataSource(
    private val repository: PhotoRepository,
    private val query: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Photo>() {

    // FOR DATA ---
    private var supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private val messageLiveData = MutableLiveData<String?>()
    private var retryQuery: (() -> Any)? =
        null // Keep reference of the last query (to be able to retry it if necessary)

    // OVERRIDE ---
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {}

    // UTILS ---
    private fun executeQuery(page: Int, perPage: Int, callback: (List<Photo>) -> Unit) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            val result = repository.search(query, perPage, page)
            retryQuery = null
            if (result is Resource.Success) {
                result.response?.photos?.photo?.let { callback(it) }
                if (result.response?.photos?.photo?.isEmpty() == true) {
                    messageLiveData.postValue("No Images Found")
                }
                networkState.postValue(NetworkState.SUCCESS)
            } else if (result is Resource.Error) {
                networkState.postValue(NetworkState.FAILED)
                messageLiveData.postValue(result.message!!)
                callback(emptyList())
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, _ ->
        networkState.postValue(NetworkState.FAILED)
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }

    // PUBLIC API ---

    fun getNetworkState(): LiveData<NetworkState> =
        networkState

    fun getMessage(): LiveData<String?> =
        messageLiveData

    fun refresh() =
        this.invalidate()

    fun retryFailedQuery() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }
}