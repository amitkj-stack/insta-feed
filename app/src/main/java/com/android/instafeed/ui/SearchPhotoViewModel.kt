package com.android.instafeed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.instafeed.data.PhotoRepository
import com.android.instafeed.datasource.PhotoDataSourceFactory
import com.android.instafeed.network.NetworkState
import com.android.instafeed.support.AppConstants
import com.android.instafeed.support.BaseViewModel

class SearchPhotoViewModel(
    repository: PhotoRepository
) : BaseViewModel() {

    private var repository: PhotoRepository = repository
    private val photoDataSource = PhotoDataSourceFactory(repository = repository, scope = ioScope)

    // OBSERVABLES ---
    val photoList = LivePagedListBuilder(photoDataSource, pagedListConfig).build()
    val networkState: LiveData<NetworkState>? =
        switchMap(photoDataSource.source) { it.getNetworkState() }
    val messageLiveData: LiveData<String?> = switchMap(photoDataSource.source) { it.getMessage() }
    // PUBLIC API ---

    /**
     * Called each time an user hits a key through [SearchView].
     */
    fun search(query: String) {
        val search = query.trim()
        if (photoDataSource.getQuery() == search) return
        photoDataSource.updateQuery(search)
    }

    /**
     * Called for last search keyword
     */
    fun search() {
        val search = repository.lastSearchedKeyword
        if (photoDataSource.getQuery() == search) return
        photoDataSource.updateQuery(search)
    }


    /**
     * Refreshes all list after an issue
     */
    fun refreshAllList() =
        photoDataSource.getSource()?.refresh()

    /**
     * Returns current search query
     */
    fun getCurrentQuery() =
        photoDataSource.getQuery()

    // UTILS ---

    val pagedListConfig: PagedList.Config
        get() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(AppConstants.PAGE_SIZE)
            .setEnablePlaceholders(false)
            .setPageSize(AppConstants.LOAD_SIZE)
            .build()
}