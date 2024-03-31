package tech.takahana.androidnavigationplayground.navigator

import tech.takahana.androidnavigationplayground.navigator.ScreenDestination.RoutePattern

interface ScreenDestination<T: RoutePattern> {

    val route: String

    fun getLocation(): ScreenLocation? = null

    interface RoutePattern {
        val value: String

        operator fun invoke() = value
    }
}