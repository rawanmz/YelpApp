package com.bignerdranch.android.yelpapp.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class RestauratViewModelTest {
    private lateinit var viewModel: RestauratViewModel
    @get:Rule
    var rule = InstantTaskExecutorRule()
    @Before
    fun setUp() {
        viewModel= RestauratViewModel(app = Application())
    }

//    @Test
//    fun fetchDataTestWithValue() {
//        viewModel.searchResturantById("1d")
//        val value = viewModel.restaurantItem.getOrAwaitValue()
//        assert(value.yelpId.isNotEmpty())
//    }
    @Test
    fun fetchDataTestWithNoValue() = runBlockingTest{
        val response: MutableLiveData<YelpRestaurant> =viewModel.searchResturantById("")
        val responseValue=response.value
        assert(responseValue?.yelpId.isNullOrEmpty())
    }
}