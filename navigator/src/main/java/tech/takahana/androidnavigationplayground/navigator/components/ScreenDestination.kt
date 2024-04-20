package tech.takahana.androidnavigationplayground.navigator.components

interface ScreenDestination {

    val route: String

    fun getLocation(): ScreenLocation? = null
}