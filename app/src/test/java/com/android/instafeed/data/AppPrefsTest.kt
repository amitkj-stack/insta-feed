package com.android.instafeed.data

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class AppPrefsTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val appPrefs = AppPrefs(context)

    @Before
    fun setUp() {
        clearSharedPrefs()
    }

    private fun clearSharedPrefs() = context
        .getSharedPreferences("${context.packageName}.prop", Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()

    @Test
    fun whenNoValueExistsShouldReturn0ByDefault() {
        val actual = appPrefs.lastSearchedKeyword
        assertThat(actual).isEqualTo(AppPrefs.NO_DATA)
    }

    @Test
    fun timestampShouldReturnCorrectValueWhenSet() {
        val lastSearchedKeyword = "Dunzo Logo"
        appPrefs.lastSearchedKeyword = lastSearchedKeyword
        val actual = appPrefs.lastSearchedKeyword
        assertThat(actual).isEqualTo(lastSearchedKeyword)
    }
}