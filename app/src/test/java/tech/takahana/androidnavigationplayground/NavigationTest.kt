package tech.takahana.androidnavigationplayground

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class NavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidAutoInjectRule(this)

    @Inject
    lateinit var navigationRobot: NavigationRobot

    @Test
    fun switchBottomNavigationItemBySelectItem() = navigationRobot {
        bootScreen()
        verifyDisplayedHomeScreen()

        navigateToTrendScreen("trendId")
        verifyDisplayedTrendScreen("trendId")
        verifySelectedBottomNavigationItemOfHome()

        switchBottomNavigationItem(
            from = MyAppScreenDestination.Home,
            to = MyAppScreenDestination.SearchTop,
        )
        verifyDisplayedSearchScreen()

        switchBottomNavigationItem(
            from = MyAppScreenDestination.SearchTop,
            to = MyAppScreenDestination.Home,
        )
        verifyDisplayedTrendScreen("trendId")
        verifySelectedBottomNavigationItemOfHome()
    }

    @Test
    fun switchBottomNavigationItemByNavigateUp() = navigationRobot {
        bootScreen()
        verifyDisplayedHomeScreen()

        navigateToTrendScreen("trendId")
        verifyDisplayedTrendScreen("trendId")
        verifySelectedBottomNavigationItemOfHome()

        switchBottomNavigationItem(
            from = MyAppScreenDestination.Home,
            to = MyAppScreenDestination.SearchTop,
        )
        verifyDisplayedSearchScreen()

        navigateUp()
        verifyDisplayedHomeScreen()
    }

    @Test
    fun navigateToStackableScreenOnBottomNavigation() = navigationRobot {
        bootScreen()
        verifyDisplayedHomeScreen()

        navigateToTrendScreen("trendId")
        verifyDisplayedTrendScreen("trendId")
        verifySelectedBottomNavigationItemOfHome()

        navigateUp()
        verifyDisplayedHomeScreen()
    }

    @Test
    fun navigateToStackableModalScreenOfActivity() = navigationRobot {
        bootScreen()
        verifyDisplayedHomeScreen()

        navigateToPlayerScreen()
        verifyDisplayedPlayerScreen()

        closePlayerScreen()
        verifyDisplayedHomeScreen()
    }

    @Test
    fun navigateToStackableModalScreenOfDialogFragment() = navigationRobot {
        bootScreen()
        verifyDisplayedHomeScreen()

        navigateToPurchaseScreen()
        verifyDisplayedPurchaseScreen()

        navigateUp()
        verifyDisplayedHomeScreen()
    }

    @Test
    fun navigateToStackableScreenFromModalScreenOfActivity() = navigationRobot {
        bootScreen()
        verifyDisplayedHomeScreen()

        navigateToPlayerScreen()
        verifyDisplayedPlayerScreen()

        closePlayerScreen()
        navigateToTrendScreen("trendId")
        verifyDisplayedTrendScreen("trendId")
    }

    @Test
    fun navigateToStackableScreenFromModalScreenOfDialogFragment() = navigationRobot {
        bootScreen()
        verifyDisplayedHomeScreen()

        navigateToPurchaseScreen()
        verifyDisplayedPurchaseScreen()

        navigateToTrendScreen("trendId")
        verifyDisplayedTrendScreen("trendId")
    }
}