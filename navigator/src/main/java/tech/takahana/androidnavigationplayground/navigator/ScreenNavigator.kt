package tech.takahana.androidnavigationplayground.navigator

interface ScreenNavigator {

    @Throws(IllegalArgumentException::class)
    fun navigate(destination: ScreenDestination<*>)
}