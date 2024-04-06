package tech.takahana.androidnavigationplayground.navigator.components

import tech.takahana.androidnavigationplayground.navigator.components.ScreenDestination.RoutePattern

interface ScreenDestination<T: RoutePattern> {

    val route: String

    fun getLocation(): ScreenLocation? = null

    interface RoutePattern {
        val value: String

        operator fun invoke() = value
    }
}