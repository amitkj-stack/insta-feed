package com.android.instafeed.data

import android.content.Context
import com.android.instafeed.R
import com.android.instafeed.network.ApiEndPoint
import com.android.instafeed.network.ApiService
import com.android.instafeed.support.AppConstants
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class PhotoRepositoryImplTest {
    private val context = mockk<Context>(relaxed = true)
    private val apiService = mockk<ApiService>(relaxed = true)
    private val appPrefs = mockk<AppPrefs>(relaxed = true)
    private val repository: PhotoRepository = PhotoRepositoryImpl(
        context,
        apiService,
        appPrefs
    )

    @Test
    fun whenNetworkIsAvailableShouldReturnSuccess() = runBlocking {
        val spy = spyk(repository)
        val endpoint = Gson().fromJson<ApiEndPoint>(
            "{\"photos\":{\"page\":1,\"pages\":37,\"perpage\":20,\"total\":728,\"photo\":[{\"id\":\"51314965996\",\"owner\":\"191519658@N07\",\"secret\":\"9d6f29ab70\",\"server\":\"65535\",\"farm\":66,\"title\":\"Develop Food Delivery App Like Dunzo\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51241760703\",\"owner\":\"192843965@N04\",\"secret\":\"4e2e1a2b75\",\"server\":\"65535\",\"farm\":66,\"title\":\"Scale Your Business Big By Launching A Cutting-edge On-demand Dunzo Clone\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51224204583\",\"owner\":\"192843965@N04\",\"secret\":\"abb148592c\",\"server\":\"65535\",\"farm\":66,\"title\":\"Conquer The On-demand Delivery Market By Launching Dunzo Clone With Compelling Features\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51218495694\",\"owner\":\"192878774@N08\",\"secret\":\"8567c8f604\",\"server\":\"65535\",\"farm\":66,\"title\":\"Food Delivery App Development Company - Made with PosterMyWall\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51189256488\",\"owner\":\"144967871@N03\",\"secret\":\"a54243a1e2\",\"server\":\"65535\",\"farm\":66,\"title\":\"Dunzo Business Model & Marketing Strategy \\u2013 A Pathway To Riches\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51189039531\",\"owner\":\"144967871@N03\",\"secret\":\"190993123a\",\"server\":\"65535\",\"farm\":66,\"title\":\"Dunzo Business Model & Marketing Strategy \\u2013 A Pathway To Riches\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51186729832\",\"owner\":\"192878774@N08\",\"secret\":\"b975fa261f\",\"server\":\"65535\",\"farm\":66,\"title\":\"How-Much-Does-it-Cost-to-Develop-an-App-like-Zomato\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51184152602\",\"owner\":\"192843965@N04\",\"secret\":\"5089cc2e25\",\"server\":\"65535\",\"farm\":66,\"title\":\"Offer a reliable delivery service to your customers with Resilient Dunzo clone\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51171355449\",\"owner\":\"84764671@N04\",\"secret\":\"309a332b3c\",\"server\":\"65535\",\"farm\":66,\"title\":\"Peal Dunzo\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51157837959\",\"owner\":\"192843965@N04\",\"secret\":\"6f9ce0dabf\",\"server\":\"65535\",\"farm\":66,\"title\":\"LAUNCH A MULTI-SERVICE ON-DEMAND DUNZO CLONE APP\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51147503237\",\"owner\":\"192843965@N04\",\"secret\":\"f6f4eb9124\",\"server\":\"65535\",\"farm\":66,\"title\":\"Step up your food delivery services with the Dunzo clone\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"51107267642\",\"owner\":\"30351938@N00\",\"secret\":\"6812dee315\",\"server\":\"65535\",\"farm\":66,\"title\":\"A-OK\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"50967763771\",\"owner\":\"14136614@N03\",\"secret\":\"3c44152297\",\"server\":\"65535\",\"farm\":66,\"title\":\"Dunzo\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"50966315987\",\"owner\":\"16445093@N03\",\"secret\":\"7a26919758\",\"server\":\"65535\",\"farm\":66,\"title\":\"Traser Is Dunzo\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"50895062568\",\"owner\":\"30351938@N00\",\"secret\":\"8c126fa071\",\"server\":\"65535\",\"farm\":66,\"title\":\"Creatures\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"50846562232\",\"owner\":\"30351938@N00\",\"secret\":\"146858bc25\",\"server\":\"65535\",\"farm\":66,\"title\":\"Water n Air\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"50687887112\",\"owner\":\"30351938@N00\",\"secret\":\"457af18983\",\"server\":\"65535\",\"farm\":66,\"title\":\"Rusty\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"50669239831\",\"owner\":\"191245777@N03\",\"secret\":\"b0acb8e05d\",\"server\":\"65535\",\"farm\":66,\"title\":\"Dunzo_logo\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"50580711542\",\"owner\":\"30351938@N00\",\"secret\":\"ca60f9cd47\",\"server\":\"65535\",\"farm\":66,\"title\":\"Oceanside\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"50374154093\",\"owner\":\"187587042@N05\",\"secret\":\"1d5fa7e148\",\"server\":\"65535\",\"farm\":66,\"title\":\"Offer multiple deliveries seamlessly with a Dunzo clone\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]},\"stat\":\"ok\"}",
            ApiEndPoint::class.java
        )
        coEvery { apiService.search(any(), any(), any(), any()).await() } returns Response.success(
            endpoint
        )
        val expected = Resource.Success(endpoint)
        val actual = spy.search("dunzo", AppConstants.PAGE_SIZE, 1)
        assertThat(actual).isInstanceOf(expected.javaClass)
    }

    @Test
    fun whenNetworkIsAvailableButStateIsNotOkShouldReturnError() = runBlocking {
        val spy = spyk(repository)
        val endpoint = Gson().fromJson(
            "{\"photos\":{\"page\":1,\"pages\":37,\"perpage\":20,\"total\":728,\"photo\":[]},\"stat\":\"false\"}",
            ApiEndPoint::class.java
        )
        coEvery { apiService.search(any(), any(), any(), any()).await() } returns Response.success(
            endpoint
        )
        val expected = Resource.Error(null)
        val actual = spy.search("dunzo", AppConstants.PAGE_SIZE, 1)
        assertThat(actual).isInstanceOf(expected.javaClass)
    }


    @Test
    fun whenNetworkIsAvailableShouldReturnError() = runBlocking {
        val spy = spyk(repository)
        coEvery { apiService.search(any(), any(), any(), any()).await() } throws IOException()
        val expected = Resource.Error(null)
        val actual = spy.search("dunzo", AppConstants.PAGE_SIZE, 1)
        assertThat(actual).isInstanceOf(expected.javaClass)
    }

    @Test
    fun whenRetrofitCallTimesOutShouldReturnError() = runBlocking {
        coEvery {
            apiService.search(any(), any(), any(), any()).await()
        } throws SocketTimeoutException()
        val expected = Resource.Error(context.getString(R.string.err_time_out))
        val actual = repository.search("dunzo", AppConstants.PAGE_SIZE, 1)
        assertThat(actual).isInstanceOf(expected.javaClass)
        assertThat((actual as Resource.Error).message).isEqualTo(expected.message)
    }

}