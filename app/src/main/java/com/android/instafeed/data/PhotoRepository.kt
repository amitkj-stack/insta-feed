package com.android.instafeed.data



interface PhotoRepository {
    /**
     * Returns a response
     */
    suspend fun search(query: String, perPage: Int, page: Int): Resource

    val lastSearchedKeyword: String
}