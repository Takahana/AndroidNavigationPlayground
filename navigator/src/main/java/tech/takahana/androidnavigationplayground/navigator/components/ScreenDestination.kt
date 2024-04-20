package tech.takahana.androidnavigationplayground.navigator.components

interface ScreenDestination {

    val route: String

    val location: ScreenLocation?
        get() = null

    val transition: ScreenTransition?
        get() = null
}