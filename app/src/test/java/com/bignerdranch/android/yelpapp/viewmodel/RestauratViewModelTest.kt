package com.bignerdranch.android.yelpapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bignerdranch.android.yelpapp.BuildConfig
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.bignerdranch.android.yelpapp.data.YelpCategory
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.repository.FakeWeatherRepo
import com.bignerdranch.android.yelpapp.repository.FakeYelpRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.bignerdranch.android.yelpapp.TestCoroutineRule
import com.bignerdranch.android.yelpapp.getOrAwaitValueTest

@ExperimentalCoroutinesApi
class RestauratViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = TestCoroutineRule()

    private val yelpFakeRepository = FakeYelpRepo()
    private val weatherFakeRepository = FakeWeatherRepo()

    private val viewModel = RestauratViewModel(
        yelpRepo = yelpFakeRepository,
        weatherRepo = weatherFakeRepository
    )

    @Before
    fun setup() {
        yelpFakeRepository.add(
            YelpRestaurant(
                "WavvLdfdP6g8aZTtbBQHTw", "name", 4.5,
                "123344455", false, 22, 4.3, "url",
                listOf(YelpCategory("title")), Coordinates(0.0, 0.0)
            )
        )

        yelpFakeRepository.addPlan(
            DayPlan(
                "WavvLdfdP6g8aZTtbBQHTw", "name", 4.5,
                "123344455", false, 22, 4.3, "url",
                listOf(YelpCategory("title")), Coordinates(0.0, 0.0), "d"
            )
        )

    }

    @Test
    fun `when fetch restaurants return value`() =
        mainCoroutineRule.dispatcher.runBlockingTest {
            val place =
                viewModel.searchRestaurant("Bearer ${BuildConfig.API_KEY}", "all", "0.0", "0.0")
                    .getOrAwaitValueTest()
            assert(place.isNotEmpty())
        }

    @Test
    fun `when search restaurant by id return value`() =
        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.searchRestaurantById("WavvLdfdP6g8aZTtbBQHTw")
            val value = viewModel.restaurantItem.getOrAwaitValueTest()
            assert(value.yelpId == "WavvLdfdP6g8aZTtbBQHTw")
        }

    @Test
    fun `when search restaurant by id does not return value`() =
        mainCoroutineRule.dispatcher.runBlockingTest {
            val value = viewModel.searchRestaurantById("").getOrAwaitValueTest()
            assert(value?.yelpId.isNullOrEmpty())
        }

    @Test
    fun `test add function `() =
        mainCoroutineRule.dispatcher.runBlockingTest {
            val plan = DayPlan(
                "WavvLdfdP6g8aZTtbBQHTw", "name", 4.5,
                "123344455", false, 22, 4.3, "url",
                listOf(YelpCategory("title")), Coordinates(0.0, 0.0), "d"
            )
            viewModel.addDayPlan(plan)
            val value = viewModel.planDays.getOrAwaitValueTest()
            assert(value.contains(plan))
        }

    @Test
    fun `test delete function `() =
        mainCoroutineRule.dispatcher.runBlockingTest {
            val plan = DayPlan(
                "WavvLdfdP6g8aZTtbBQHTw", "name", 4.5,
                "123344455", false, 22, 4.3, "url",
                listOf(YelpCategory("title")), Coordinates(0.0, 0.0), "d"
            )
            viewModel.deleteDayPlan(plan)
            val value = viewModel.planDays.getOrAwaitValueTest()
            assert(!value.contains(plan))
        }

    @Test
    fun `test update function `() =
        mainCoroutineRule.dispatcher.runBlockingTest {
            val plan = DayPlan(
                "WavvLdfdP6g8aZTtbBQHTw", "name", 4.5,
                "123344455", false, 22, 4.3, "url",
                listOf(YelpCategory("title")), Coordinates(0.0, 0.0), "d"
            )
            viewModel.addDayPlan(plan)
            viewModel.updateDayPlan(plan)
            val value = viewModel.planDays.getOrAwaitValueTest()
            assert(value.contains(plan))
        }

    @Test
    fun `when search plan by id return value`() =
        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.searchDayPlan("WavvLdfdP6g8aZTtbBQHTw")
            val value = viewModel.dayPlan.getOrAwaitValueTest()
            assert(value.yelpId == "WavvLdfdP6g8aZTtbBQHTw")
        }

    @Test
    fun `test fetch weather return value`() =
        mainCoroutineRule.dispatcher.runBlockingTest {

            assert(weatherFakeRepository.weather.forecast.forecastday.isNotEmpty())
        }
}