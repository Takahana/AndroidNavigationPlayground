package tech.takahana.androidnavigationplayground.navigator.components

class DefaultScreenNavigator(
    private val dispatcher: ScreenNavigationDispatcher,
) : ScreenNavigator {
    override fun navigate(destination: ScreenDestination) {
        dispatcher.dispatch(destination)
    }
}