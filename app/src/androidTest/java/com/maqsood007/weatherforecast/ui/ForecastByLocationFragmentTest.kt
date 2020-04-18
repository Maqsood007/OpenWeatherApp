package com.maqsood007.weatherforecast.ui


import android.content.Context
import android.view.View
import androidx.appcompat.view.menu.ActionMenuItem
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.WeatherApp
import com.maqsood007.weatherforecast.di.DaggerWeatherAppTestComponent
import com.maqsood007.weatherforecast.di.WeatherAppTestComponent
import com.maqsood007.weatherforecast.rule.DaggerActivityTestRule
import com.maqsood007.weatherforecast.rule.DaggerActivityTestRule.OnBeforeActivityLaunchedListener
import com.maqsood007.weatherforecast.ui.forcasts.current_location.ForcastByLocationFragment
import com.maqsood007.weatherforecast.utils.DateTimeUtility
import com.maqsood007.weatherforecast.utils.DateTimeUtility.DATE_FORMATTER
import com.maqsood007.weatherforecast.utils.DateTimeUtility.toSpecificFormat
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Created by Muhammad Maqsood on 11/04/2020.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ForecastByLocationFragmentTest {


    val todayName = DateTimeUtility.getDayName(
        Date().toSpecificFormat(DATE_FORMATTER),
        DATE_FORMATTER
    )

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = DaggerActivityTestRule<MainActivity>(
        MainActivity::class.java,
        object : OnBeforeActivityLaunchedListener<MainActivity> {

            override fun beforeActivityLaunched(
                weatherApp: WeatherApp,
                activity: MainActivity?
            ) {
                val forecastAppComponent: WeatherAppTestComponent = DaggerWeatherAppTestComponent
                    .builder()
                    .application(weatherApp)
                    .build()

                weatherApp.setDaggerForecastComponent(forecastAppComponent = forecastAppComponent)
            }
        })

    @Rule
    @JvmField
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_temperatureListContainerIsDisplayed() {
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }


    @Test
    fun test_today_main_info__visible() {
        onView(
            withId(R.id.tvTodayMainTemprature)
        ).check(matches(validateDefaultVisibility(View.VISIBLE)))

        onView(
            withId(R.id.tvtodayMainDate)
        ).check(matches(validateDefaultVisibility(View.VISIBLE)))

        onView(
            withId(R.id.tvMainDescription)
        ).check(matches(validateDefaultVisibility(View.VISIBLE)))

        onView(
            withId(R.id.tvMainTodayTempRange)
        ).check(matches(validateDefaultVisibility(View.VISIBLE)))

        onView(
            withId(R.id.tvMainTodayWinds)
        ).check(matches(validateDefaultVisibility(View.VISIBLE)))

        onView(withId(R.id.imgDescription)).check(matches(validateDefaultVisibility(View.VISIBLE)))

        onView(withId(R.id.imgTempRange)).check(matches(validateDefaultVisibility(View.VISIBLE)))

        onView(withId(R.id.imgWinds)).check(matches(validateDefaultVisibility(View.VISIBLE)))
    }


    @Test
    fun test_today_main_textview_data_binded() {
        onView(withId(R.id.tvTodayMainTemprature)).check(matches(not(withText(""))))
        onView(withId(R.id.tvtodayMainDate)).check(matches(not(withText(""))))
        onView(withId(R.id.tvMainDescription)).check(matches(not(withText(""))))
        onView(withId(R.id.tvMainTodayTempRange)).check(matches(not(withText(""))))
        onView(withId(R.id.tvMainTodayWinds)).check(matches(not(withText(""))))
    }

    private fun validateDefaultVisibility(visibility: Int): Matcher<View> {

        val boundedMatcher = object : BoundedMatcher<View, View>(View::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("Checking visibility is visible or not")
            }

            override fun matchesSafely(item: View?): Boolean {
                return item?.visibility == visibility
            }
        }

        return boundedMatcher
    }

    @Test
    fun testToday_name_displayed_first_row() {
        onView(withId(R.id.recyclerView))
            .check(
                matches(
                    hasTodayNameForGivenPosition(
                        0,
                        todayName
                    )
                )
            )

    }


    @Test
    fun test_second_list_item_has_correct_name_wrt_today() {
        onView(withId(R.id.recyclerView))
            .check(
                matches(
                    hasTodayNameForGivenPosition(
                        1,
                        getMappedDays(todayName, 1)
                    )
                )
            )

    }

    @Test
    fun test_third_list_item_has_correct_name_wrt_today() {
        onView(withId(R.id.recyclerView))
            .check(
                matches(
                    hasTodayNameForGivenPosition(
                        2,
                        getMappedDays(todayName, 2)
                    )
                )
            )

    }


    @Test
    fun test_fourth_list_item_has_correct_name_wrt_today() {
        onView(withId(R.id.recyclerView))
            .check(
                matches(
                    hasTodayNameForGivenPosition(
                        3,
                        getMappedDays(todayName, 3)
                    )
                )
            )

    }

    @Test
    fun test_fifth_list_item_has_correct_name_wrt_today() {
        onView(withId(R.id.recyclerView))
            .check(
                matches(
                    hasTodayNameForGivenPosition(
                        4,
                        getMappedDays(todayName, 4)
                    )
                )
            )

    }

    @Test
    fun test_sixth_list_item_has_correct_name_wrt_today() {
        onView(withId(R.id.recyclerView))
            .check(
                matches(
                    hasTodayNameForGivenPosition(
                        5,
                        getMappedDays(todayName, 5)
                    )
                )
            )

    }



    @Test
    fun test_main_temp_always_equal_list_zero_index_temperature(){


    }



    private fun hasTodayNameForGivenPosition(position: Int, today: String): Matcher<View> {

        val recyclerBounderMatcher =
            object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("Is day name displayed correctly")
                }

                override fun matchesSafely(recyclerView: RecyclerView?): Boolean {

                    val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position)
                    return withChild(withText(today)).matches(viewHolder?.itemView)
                }
            }

        return recyclerBounderMatcher
    }

    @Test
    fun isViewModelGeneratedFromViewModelFactory() {
        val mockNavController = Mockito.mock(NavController::class.java)
        val scenario = launchFragmentInContainer<ForcastByLocationFragment>()
        val context: Context = ApplicationProvider.getApplicationContext<WeatherApp>()
        val addMenuItem = ActionMenuItem(context, 0, R.id.action_search, 0, 0, null)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            fragment.onOptionsItemSelected(addMenuItem)
            verify(mockNavController).navigate(R.id.action_forcastByLocationFragment_to_selectCitiesFragment)
        }
    }

    @After
    fun tearDown() {

    }


    private fun getMappedDays(dayName: String, position: Int): String {

        when (dayName) {

            "Saturday" -> {
                when (position) {
                    0 -> return "Saturday"
                    1 -> return "Sunday"
                    2 -> return "Monday"
                    3 -> return "Tuesday"
                    4 -> return "Wednesday"
                    else -> return "Thursday"
                }
            }
            "Sunday" -> {
                when (position) {
                    0 -> return "Sunday"
                    1 -> return "Monday"
                    2 -> return "Tuesday"
                    3 -> return "Wednesday"
                    4 -> return "Thursday"
                    else -> return "Friday"
                }
            }
            "Monday" -> {
                when (position) {
                    0 -> return "Monday"
                    1 -> return "Tuesday"
                    2 -> return "Wednesday"
                    3 -> return "Thursday"
                    4 -> return "Friday"
                    else -> return "Saturday"
                }
            }
            "Tuesday" -> {
                when (position) {
                    0 -> return "Tuesday"
                    1 -> return "Wednesday"
                    2 -> return "Thursday"
                    3 -> return "Friday"
                    4 -> return "Saturday"
                    else -> return "Sunday"
                }
            }
            "Wednesday" -> {
                when (position) {
                    0 -> return "Wednesday"
                    1 -> return "Thursday"
                    2 -> return "Friday"
                    3 -> return "Saturday"
                    4 -> return "Sunday"
                    else -> return "Monday"
                }
            }
            "Thursday" -> {
                when (position) {
                    0 -> return "Thursday"
                    1 -> return "Friday"
                    2 -> return "Saturday"
                    3 -> return "Sunday"
                    4 -> return "Monday"
                    else -> return "Tuesday"
                }
            }
            else -> {
                when (position) {
                    0 -> return "Friday"
                    1 -> return "Saturday"
                    2 -> return "Sunday"
                    3 -> return "Monday"
                    4 -> return "Tuesday"
                    else -> return "Wednesday"
                }
            }

        }

    }

}