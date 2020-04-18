package com.maqsood007.weatherforecast.ui


import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.maqsood007.weatherforecast.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {


    // Only Access_fine_location doing the work.

    @Rule
    @JvmField
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun grantPhonePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().uiAutomation.executeShellCommand(
                "pm grant " + getApplicationContext<Context>()
                    .packageName
                        + " android.permission.ACCESS_FINE_LOCATION"
            )
        }
    }


    @Test
    fun mainActivityTest() {
        val frameLayout = onView(
            allOf(
                withId(R.id.fragmentContainer),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    // Find a view with no id.
    @Test
    fun testToolbarTitle_textView_visible() {
        onView(
            AllOf.allOf(
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(Toolbar::class.java)),
                ViewMatchers.isAssignableFrom(AppCompatTextView::class.java)
            )
        ).check(
            matches(validateDefaultVisibility(View.VISIBLE))
        )
    }



    @Test
    fun testToolbarTitle_textView_data_binded() {
        onView(
            AllOf.allOf(
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(Toolbar::class.java)),
                ViewMatchers.isAssignableFrom(AppCompatTextView::class.java)
            )
        ).check(
            matches(withText("Mountain View"))
        )
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

}
