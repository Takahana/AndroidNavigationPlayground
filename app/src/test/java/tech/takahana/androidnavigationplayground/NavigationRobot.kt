package tech.takahana.androidnavigationplayground

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isNotSelected
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.common.truth.Truth.assertThat
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import tech.takahana.androidnavigationplayground.home.HomeFragment
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationRequestDispatcher
import tech.takahana.androidnavigationplayground.player.PlayerActivity
import tech.takahana.androidnavigationplayground.purchase.PurchaseBottomSheetDialogFragment
import tech.takahana.androidnavigationplayground.purchase.PurchaseFragment
import tech.takahana.androidnavigationplayground.search.SearchFragment
import tech.takahana.androidnavigationplayground.trend.TrendFragment
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel
import javax.inject.Inject
import tech.takahana.androidnavigationplayground.purchase.R.id as PurchaseRId

class NavigationRobot @Inject constructor(
    private val screenNavigationRequestDispatcher: ScreenNavigationRequestDispatcher,
) {

    private val bottomNavMenuHomeId = R.id.menu_main_bottom_home
    private val bottomNavMenuSearchId = R.id.menu_main_bottom_search

    private lateinit var mainFragment: MainFragment
    private lateinit var activityScenario: ActivityScenario<HiltTestActivity>

    fun bootScreen() {
        activityScenario = launchFragmentInHiltContainer<MainFragment> {
            mainFragment = this as MainFragment
        }
    }

    fun switchBottomNavigationItem(from: MyAppScreenDestination, to: MyAppScreenDestination) {
        onView(withId(from.bottomNavMenuId())).check(matches(isSelected()))
        onView(withId(to.bottomNavMenuId())).check(matches(isNotSelected()))

        onView(withId(to.bottomNavMenuId())).perform(click())

        onView(withId(from.bottomNavMenuId())).check(matches(isNotSelected()))
        onView(withId(to.bottomNavMenuId())).check(matches(isSelected()))
    }

    fun verifyDisplayedHomeScreen() {
        assertThat(fragmentOnMainFragmentContainer()).isInstanceOf(HomeFragment::class.java)
        verifySelectedBottomNavigationItemOfHome()
    }

    fun verifyDisplayedSearchScreen() {
        assertThat(fragmentOnMainFragmentContainer()).isInstanceOf(SearchFragment::class.java)
        verifySelectedBottomNavigationItemOfSearch()
    }

    fun verifyDisplayedTrendScreen(trendId: String) {
        val fragment = fragmentOnMainFragmentContainer()
        val args = fragment.requireArguments()
        assertThat(fragment).isInstanceOf(TrendFragment::class.java)
        assertThat(args.getString(MyAppScreenDestination.Trend.Key.TREND_ID)).isEqualTo(trendId)
    }

    fun verifyDisplayedPurchaseScreen() {
        assertThat(fragmentOnPurchaseBottomSheetDialogFragment())
            .isInstanceOf(PurchaseFragment::class.java)
    }

    fun verifyDisplayedPlayerScreen() {
        val nextStartedActivity = shadowOf(RuntimeEnvironment.getApplication()).nextStartedActivity
        assertThat(nextStartedActivity.component?.className).isEqualTo(PlayerActivity::class.java.name)
    }

    fun navigateToTrendScreen(
        trendId: String,
    ) {
        navigateTo(
            MyAppScreenDestination.Trend(TrendIdUiModel(trendId))
        )
    }

    fun navigateToPlayerScreen() {
        navigateTo(
            MyAppScreenDestination.Player
        )
        moveMainActivityToBackground()
    }

    fun navigateToPurchaseScreen() {
        navigateTo(
            MyAppScreenDestination.Purchase
        )
    }

    fun verifySelectedBottomNavigationItemOfHome() {
        onView(withId(MyAppScreenDestination.Home.bottomNavMenuId())).check(matches(isSelected()))
    }

    fun verifySelectedBottomNavigationItemOfSearch() {
        onView(withId(MyAppScreenDestination.SearchTop.bottomNavMenuId())).check(matches(isSelected()))
    }

    fun navigateUp() {
        mainFragment.navHostFragment.navController.navigateUp()
        mainFragment.navHostFragment.childFragmentManager.executePendingTransactions()
    }

    fun closePlayerScreen() {
        moveMainActivityToForeground()
    }

    private fun fragmentOnMainFragmentContainer(): Fragment {
        return requireNotNull(
            mainFragment.navHostFragment.childFragmentManager.findFragmentById(R.id.main_fragment_container)
        )
    }

    private fun fragmentOnPurchaseBottomSheetDialogFragment(): Fragment {
        return requireNotNull(
            mainFragment.navHostFragment.childFragmentManager.fragments
                .first { it is PurchaseBottomSheetDialogFragment }
                .childFragmentManager.findFragmentById(
                    PurchaseRId.bottom_sheet_dialog_fragment_purchase_container
                )
        )
    }

    private fun navigateTo(destination: MyAppScreenDestination) {
        screenNavigationRequestDispatcher.dispatch(destination, null)
        mainFragment.navHostFragment.childFragmentManager.executePendingTransactions()
    }

    private fun moveMainActivityToBackground() {
        activityScenario.moveToState(Lifecycle.State.CREATED)
    }

    private fun moveMainActivityToForeground() {
        shadowOf(RuntimeEnvironment.getApplication()).clearNextStartedActivities()
        activityScenario.moveToState(Lifecycle.State.RESUMED)
    }

    operator fun invoke(block: NavigationRobot.() -> Unit) {
        block()
    }

    private fun MyAppScreenDestination.bottomNavMenuId(): Int {
        return when (this) {
            MyAppScreenDestination.Home -> bottomNavMenuHomeId
            MyAppScreenDestination.SearchTop -> bottomNavMenuSearchId
            else -> throw IllegalArgumentException("Failed mapping destination: $this to bottomNavMenuId")
        }
    }
}