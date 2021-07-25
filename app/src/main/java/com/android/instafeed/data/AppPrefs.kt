package com.android.instafeed.data

import android.content.Context

class AppPrefs(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(
        "${context.packageName}.prop",
        Context.MODE_PRIVATE
    )
    private val editor = sharedPrefs.edit()

    var lastSearchedKeyword: String
        get() = (sharedPrefs.getString(LAST_SEARCHED_KEYWORD, NO_DATA) ?: NO_DATA)
        set(value) = editor.putString(LAST_SEARCHED_KEYWORD, value).apply()

    companion object {
        const val LAST_SEARCHED_KEYWORD = "LAST_SEARCHED_KEYWORD"
        const val NO_DATA = "Dunzo"
    }
}