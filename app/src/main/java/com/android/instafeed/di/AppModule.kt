package com.android.instafeed.di

import android.content.Context
import com.android.instafeed.BuildConfig
import com.android.instafeed.data.AppPrefs
import com.android.instafeed.data.PhotoRepository
import com.android.instafeed.data.PhotoRepositoryImpl
import com.android.instafeed.network.ApiService
import com.android.instafeed.support.Utils.isNetworkAvailable
import com.android.instafeed.ui.SearchPhotoViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideSharedPreferences(androidContext()) }
    single { provideOkHttpClient(androidContext()) }
    single { provideGson() }
    single { provideRetrofit(get(), get(), BuildConfig.BASE_URL) }
    single { provideApiService(get()) }
    single<PhotoRepository> {
        PhotoRepositoryImpl(get(), get(), get())
    }
    // MyViewModel ViewModel
    viewModel {
        SearchPhotoViewModel(get())
    }
}

fun provideSharedPreferences(context: Context): AppPrefs {
    return AppPrefs(context)
}

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

fun provideGson(): Gson {
    return GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
}

private fun provideOkHttpClient(androidContext: Context): OkHttpClient? {
    val cacheSize = (5 * 1024 * 1024).toLong()
    val myCache = Cache(androidContext.cacheDir, cacheSize)
    return if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(getCacheInterceptor(androidContext))
            .build()
    } else OkHttpClient
        .Builder()
        .cache(myCache)
        .addInterceptor(getCacheInterceptor(androidContext))
        .build()
}

fun getCacheInterceptor(context: Context): Interceptor {
    return Interceptor { chain ->
        // Get the request from the chain.
        var request = chain.request()

        /*
        *  Leveraging the advantage of using Kotlin,
        *  we initialize the request and change its header depending on whether
        *  the device is connected to Internet or not.
        */
        request = if (context.isNetworkAvailable())
        /*
        *  If there is Internet, get the cache that was stored 5 seconds ago.
        *  If the cache is older than 5 seconds, then discard it,
        *  and indicate an error in fetching the response.
        *  The 'max-age' attribute is responsible for this behavior.
        */
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        else
        /*
        *  If there is no Internet, get the cache that was stored 7 days ago.
        *  If the cache is older than 7 days, then discard it,
        *  and indicate an error in fetching the response.
        *  The 'max-stale' attribute is responsible for this behavior.
        *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
        */
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                .build()
        // End of if-else statement

        // Add the modified request to the chain.
        chain.proceed(request)
    }
}
