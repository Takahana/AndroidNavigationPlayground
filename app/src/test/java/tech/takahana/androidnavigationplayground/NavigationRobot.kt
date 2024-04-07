package tech.takahana.androidnavigationplayground

import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isNotSelected
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.common.truth.Truth.assertThat
import tech.takahana.androidnavigationplayground.home.HomeFragment
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationDispatcher
import tech.takahana.androidnavigationplayground.search.SearchFragment
import tech.takahana.androidnavigationplayground.trend.TrendFragment
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel
import javax.inject.Inject

class NavigationRobot @Inject constructor(
    private val screenNavigationDispatcher: ScreenNavigationDispatcher,
) {

    private val bottomNavMenuHomeId = R.id.menu_main_bottom_home
    private val bottomNavMenuSearchId = R.id.menu_main_bottom_search

    private lateinit var mainFragment: MainFragment

    fun bootScreen() {
        launchFragmentInHiltContainer<MainFragment>() {
            mainFragment = this as MainFragment
        }
    }

    fun switchBottomNavigationItem(from: MyAppScreenDestination<*>, to: MyAppScreenDestination<*>) {
        onView(withId(from.bottomNavMenuId())).check(matches(isSelected()))
        onView(withId(to.bottomNavMenuId())).check(matches(isNotSelected()))

        onView(withId(to.bottomNavMenuId())).perform(click())

        onView(withId(from.bottomNavMenuId())).check(matches(isNotSelected()))
        onView(withId(to.bottomNavMenuId())).check(matches(isSelected()))
    }

    fun displayingHomeScreen() {
        assertThat(fragmentOnMainFragmentContainer()).isInstanceOf(HomeFragment::class.java)
        onView(withId(MyAppScreenDestination.Home.bottomNavMenuId())).check(matches(isSelected()))
    }

    fun displayingSearchScreen() {
        assertThat(fragmentOnMainFragmentContainer()).isInstanceOf(SearchFragment::class.java)
        onView(withId(MyAppScreenDestination.Search.bottomNavMenuId())).check(matches(isSelected()))
    }

    fun displayingTrendScreen(trendId: String) {
        val fragment = fragmentOnMainFragmentContainer()
        val args = fragment.requireArguments()
        assertThat(fragment).isInstanceOf(TrendFragment::class.java)
        assertThat(args.getString(MyAppScreenDestination.Trend.Key.TREND_ID)).isEqualTo(trendId)
    }

    fun navigateToTrendScreen(
        trendId: String,
    ) {
        navigateTo(
            MyAppScreenDestination.Trend(TrendIdUiModel(trendId))
        )
    }

    fun navigateUp() {
        mainFragment.navHostFragment.navController.navigateUp()
        mainFragment.navHostFragment.childFragmentManager.executePendingTransactions()
    }

    private fun fragmentOnMainFragmentContainer(): Fragment {
        return requireNotNull(
            mainFragment.navHostFragment.childFragmentManager.findFragmentById(R.id.main_fragment_container)
        )
    }

    private fun navigateTo(destination: MyAppScreenDestination<*>) {
        screenNavigationDispatcher.dispatch(destination)
        mainFragment.navHostFragment.childFragmentManager.executePendingTransactions()
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