package tech.takahana.androidnavigationplayground.navigator

interface ScreenDestination {
    val routePattern: String

    fun route(): String
}