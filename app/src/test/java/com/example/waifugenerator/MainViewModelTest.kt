package com.example.waifugenerator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.waifugenerator.Repository.WaifuRepo
import com.example.waifugenerator.Retrofit.Resource
import com.example.waifugenerator.models.Waifu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MainViewModelTest {

    private var testDispatcher= StandardTestDispatcher()   //mocking main with dispatcher block

    lateinit var savedState : SavedStateHandle              //mock savedState

    @get:Rule                                             // runs before "before"
    var instantExecutorRule = InstantTaskExecutorRule()   //makes it synchronous!!

    @Mock
    lateinit var waifuRepo: WaifuRepo                      //mock repo

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)                // no context so mock main
         savedState = SavedStateHandle(mapOf("url" to "https://i.waifu.pics/M8G5bSN.gif"))
    }


    @Test
    fun getWaifu_empty_expectedEmpty() = runTest{
        Mockito.`when`(waifuRepo.getWaifuURL("")).thenReturn(Response.success(Waifu("")))

        val mainViewModelTest= MainViewModel(waifuRepo,savedState)

         mainViewModelTest.getWaifu("")

        testDispatcher.scheduler.advanceUntilIdle()                     // Runs the enqueued tasks in the specified order

        val result = mainViewModelTest.mWaifuLink.getOrAwaitValue()     // getOrAwaitValue() -> mock observeForever but close after 1

        Assert.assertEquals(0,result.data?.length)
    }

    @Test
    fun getWaifu_val_expectedVal() = runTest{
        Mockito.`when`(waifuRepo.getWaifuURL("a")).thenReturn(Response.success(Waifu("a.html")))

        val mainViewModelTest= MainViewModel(waifuRepo,savedState)

        mainViewModelTest.getWaifu("a")

        testDispatcher.scheduler.advanceUntilIdle()                     // Runs the enqueued tasks in the specified order

        val result = mainViewModelTest.mWaifuLink.getOrAwaitValue()     // getOrAwaitValue() -> mock observeForever but close after 1

        Assert.assertEquals("a.html",result.data)
        Assert.assertEquals("SUCCESS",result.status.name)
    }

    @Test
    fun getWaifu_error_expectedError() = runTest{
        Mockito.`when`(waifuRepo.getWaifuURL("error")).thenReturn(Response.error(500, ResponseBody.create(null,"Error getting waifu")))

        val mainViewModelTest= MainViewModel(waifuRepo,savedState)

        mainViewModelTest.getWaifu("error")

        testDispatcher.scheduler.advanceUntilIdle()                     // Runs the enqueued tasks in the specified order

        val result = mainViewModelTest.mWaifuLink.getOrAwaitValue()     // getOrAwaitValue() -> mock observeForever but close after 1

        Assert.assertEquals("Error getting waifu",result.error?.message)
    }

    @After
    fun tearDown() {
    }
}