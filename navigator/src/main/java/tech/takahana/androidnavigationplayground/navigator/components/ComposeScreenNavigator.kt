package tech.takahana.androidnavigationplayground.navigator.components

import androidx.navigation.NavHostController
import androidx.navigation.contains
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class ComposeScreenNavigator(
    private val navController: NavHostController,
    private val navigationMessageDispatcher: ScreenNavigationMessageDispatcher,
    private val navigationRequestDispatcher: ScreenNavigationRequestDispatcher,
) : ScreenNavigator {
    override val requestTag: String = ScreenNavigationRequestTagCreator.createTag()
    override val screenNavigationMessage: Flow<ScreenNavigationMessage> =
        navigationMessageDispatcher.screenNavigationMessage.filterNotNull()

    override fun navigate(destination: ScreenDestination) {
        if (navController.graph.contains(destination.route)) {
            navController.navigate(destination.route)
        } else {
            navigationRequestDispatcher.dispatch(destination, requestTag)
        }
    }

    override fun respondedTo(message: ScreenNavigationMessage) {
        navigationMessageDispatcher.responded(message)
    }

    class Factory(
        private val navigationMessageDispatcher: ScreenNavigationMessageDispatcher,
        private val navigationRequestDispatcher: ScreenNavigationRequestDispatcher,
    ) {
        fun create(
            navController: NavHostController,
        ): ScreenNavigator {
            return ComposeScreenNavigator(
                navController,
                navigationMessageDispatcher,
                navigationRequestDispatcher
            )
        }
    }
}