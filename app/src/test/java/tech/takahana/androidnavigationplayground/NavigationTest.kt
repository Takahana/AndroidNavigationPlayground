package tech.takahana.androidnavigationplayground

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class NavigationTest {

    private lateinit var navigationRobot: NavigationRobot

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        navigationRobot = NavigationRobot()
    }

    @Test
    fun switchBottomNavigationItem() = navigationRobot {
        bootScreen()
        switchBottomNavigationItem(
            from = MyAppScreenDestination.Home,
            to = MyAppScreenDestination.Search,
        )
    }
}