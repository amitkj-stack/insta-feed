package com.android.instafeed.network

/**
 * Used to notify a client
 * of the actual state of a query
 */
enum class NetworkState {
    RUNNING,
    SUCCESS,
    FAILED
}