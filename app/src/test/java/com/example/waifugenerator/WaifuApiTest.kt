package com.example.waifugenerator

import com.example.waifugenerator.Retrofit.waifuAPI
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WaifuApiTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var waifuApi: waifuAPI

    @Before
    fun setup(){
        mockWebServer=MockWebServer()

        waifuApi=Retrofit.Builder().baseUrl(mockWebServer.url("/")).addConverterFactory(GsonConverterFactory.create())
            .build().create(waifuAPI::class.java)
    }

    @Test
    fun waifuApiTest_empty_expectedEmpty() = runTest{

        val mockResponse=MockResponse()
        mockResponse.setBody("{\"url\":\"\"}")

        mockWebServer.enqueue(mockResponse)
        val response = waifuApi.getWaifu(mockWebServer.url("/").toString())
        mockWebServer.takeRequest()

        Assert.assertEquals(0, response.body()?.url?.length)
    }

    @Test
    fun waifuApiTest_val_expectedVal() = runTest{

        val mockResponse=MockResponse()
        mockResponse.setBody("{\"url\":\"https://i.waifu.pics/7Z1tV23.gif\"}")

        mockWebServer.enqueue(mockResponse)
        val response = waifuApi.getWaifu(mockWebServer.url("/").toString())
        mockWebServer.takeRequest()

        Assert.assertEquals("https://i.waifu.pics/7Z1tV23.gif", response.body()?.url)
    }

    @Test
    fun waifuApiTest_Error_expectedInternalServer() = runTest{

        val mockResponse=MockResponse()
        mockResponse.setBody("something went wrong")
        mockResponse.setResponseCode(500)

        mockWebServer.enqueue(mockResponse)
        val response = waifuApi.getWaifu(mockWebServer.url("/").toString())
        mockWebServer.takeRequest()

        Assert.assertEquals(false,response.isSuccessful)
        Assert.assertEquals(500,response.code())
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

}