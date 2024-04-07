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
    fun switchBottomNavigationItem() = navigationRobot {
        bootScreen()
        displayHomeScreen()
        switchBottomNavigationItem(
            from = MyAppScreenDestination.Home,
            to = MyAppScreenDestination.Search,
        )
        displaySearchScreen()
    }

    @Test
    fun navigateToStackableScreen() = navigationRobot {
        bootScreen()
        displayHomeScreen()
        navigateToTrendScreen("trendId")
        displayTrendScreen("trendId")
        navigateUp()
        displayHomeScreen()
    }
}