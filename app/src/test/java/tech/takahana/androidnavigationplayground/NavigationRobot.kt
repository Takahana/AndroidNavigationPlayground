package tech.takahana.androidnavigationplayground

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isNotSelected
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination

class NavigationRobot {

    private val bottomNavMenuHomeId = R.id.menu_main_bottom_home
    private val bottomNavMenuSearchId = R.id.menu_main_bottom_search

    fun bootScreen() {
        launchFragmentInHiltContainer<MainFragment>()
    }

    fun switchBottomNavigationItem(from: MyAppScreenDestination<*>, to: MyAppScreenDestination<*>) {
        onView(withId(from.bottomNavMenuId())).check(matches(isSelected()))
        onView(withId(to.bottomNavMenuId())).check(matches(isNotSelected()))

        onView(withId(to.bottomNavMenuId())).perform(click())

        onView(withId(from.bottomNavMenuId())).check(matches(isNotSelected()))
        onView(withId(to.bottomNavMenuId())).check(matches(isSelected()))
    }

    operator fun invoke(block: NavigationRobot.() -> Unit) {
        block()
    }

    private fun MyAppScreenDestination<*>.bottomNavMenuId(): Int {
        return when (this) {
            MyAppScreenDestination.Home -> bottomNavMenuHomeId
            MyAppScreenDestination.Search -> bottomNavMenuSearchId
            else -> throw IllegalArgumentException("Failed mapping destination: $this to bottomNavMenuId")
        }
    }
}