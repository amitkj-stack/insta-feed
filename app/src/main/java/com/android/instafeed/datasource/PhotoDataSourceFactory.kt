package com.android.instafeed.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.android.instafeed.data.Photo
import com.android.instafeed.data.PhotoRepository
import kotlinx.coroutines.CoroutineScope

class PhotoDataSourceFactory(
    private val repository: PhotoRepository,
    private var query: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Photo>() {

    val source = MutableLiveData<PhotoDataSource>()


    // --- PUBLIC API

    fun getQuery() = query

    fun getSource() = source.value

    fun updateQuery(query: String) {
        this.query = query
        getSource()?.refresh()
    }

    override fun create(): DataSource<Int, Photo> {
        val source = PhotoDataSource(repository, query, scope)
        this.source.postValue(source)
        return source
    }
}