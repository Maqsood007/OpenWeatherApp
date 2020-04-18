package com.maqsood007.weatherforecast.ui


import android.content.Context
import android.util.Log
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
import androidx.test.platform.app.InstrumentationRegistry
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


    @Rule @JvmField var activityRule: ActivityTestRule<MainActivity> = DaggerActivityTestRule<MainActivity>(
        MainActivity::class.java,
        object : OnBeforeActivityLaunchedListener<MainActivity> {

            override fun beforeActivityLaunched(
                weatherApp: WeatherApp,
                activity: MainActivity?
            ) {
                val forecastAppComponent : WeatherAppTestComponent = DaggerWeatherAppTestComponent
                    .builder()
                    .application(weatherApp)
                    .build()

                weatherApp.setDaggerForecastComponent(forecastAppComponent = forecastAppComponent)

                Log.d("","")
            }


        })

    @Rule
    @JvmField
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)


//    @Rule
//    @JvmField
//    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    private fun getApp(): WeatherApp? {
        return InstrumentationRegistry.getInstrumentation()
            .getTargetContext().getApplicationContext() as WeatherApp
    }

    @Test
    fun temperatureListContainerIsDisplayed() {
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }


    @Test
    fun today_temprature_textview_visible() {
        onView(
            withId(R.id.tvTodayMainTemprature)
        ).check(matches(validateDefaultVisibility(View.VISIBLE)))
    }

    @Test
    fun today_temprature_textview_data_binded() {
        onView(withId(R.id.tvTodayMainTemprature)).check(matches(not(withText(""))))
    }

    @Test
    fun today_temprature_img_not_visible() {
        onView(withId(R.id.imgDescription)).check(matches(validateDefaultVisibility(View.VISIBLE)))
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
    fun testToday_name_displayer_first_row() {
        onView(withId(R.id.recyclerView))
            .check(
                matches(
                    hasTodayNameForGivenPosition(
                        0,
                        DateTimeUtility.getDayName(
                            Date().toSpecificFormat(DATE_FORMATTER),
                            DATE_FORMATTER
                        )
                    )
                )
            )

    }

    private fun hasTodayNameForGivenPosition(position: Int, today: String): Matcher<View> {

        val recyclerBounderMatcher =
            object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("Is today name displayed")
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

}