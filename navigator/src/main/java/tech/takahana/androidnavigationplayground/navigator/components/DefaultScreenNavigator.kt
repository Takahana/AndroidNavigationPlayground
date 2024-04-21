package tech.takahana.androidnavigationplayground.navigator.components

class DefaultScreenNavigator(
    private val navigationRequestDispatcher: ScreenNavigationRequestDispatcher,
) : ScreenNavigator {
    override fun navigate(destination: ScreenDestination) {
        navigationRequestDispatcher.dispatch(destination)
    }
}