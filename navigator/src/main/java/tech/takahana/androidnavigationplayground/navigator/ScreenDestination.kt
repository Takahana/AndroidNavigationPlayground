package tech.takahana.androidnavigationplayground.navigator

import tech.takahana.androidnavigationplayground.navigator.ScreenDestination.RoutePattern

interface ScreenDestination<T: RoutePattern> {
    fun route(): String

    interface RoutePattern {
        val value: String
    }
}