package tech.takahana.androidnavigationplayground.navigator.components

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class DefaultScreenNavigator(
    private val navigationMessageDispatcher: ScreenNavigationMessageDispatcher,
    private val navigationRequestDispatcher: ScreenNavigationRequestDispatcher,
) : ScreenNavigator {
    override val screenNavigationMessage: Flow<ScreenNavigationMessage> =
        navigationMessageDispatcher.screenNavigationMessage.filterNotNull()

    override fun navigate(destination: ScreenDestination) {
        navigate(destination, null)
    }

    override fun navigate(
        destination: ScreenDestination,
        requestTag: String?,
    ) {
        navigationRequestDispatcher.dispatch(destination, requestTag)
    }

    override fun respondedTo(message: ScreenNavigationMessage) {
        navigationMessageDispatcher.responded(message)
    }
}