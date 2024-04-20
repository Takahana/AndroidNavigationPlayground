package tech.takahana.androidnavigationplayground.navigator.components

interface ScreenNavigator {

    @Throws(IllegalArgumentException::class)
    fun navigate(destination: ScreenDestination)

    companion object
}