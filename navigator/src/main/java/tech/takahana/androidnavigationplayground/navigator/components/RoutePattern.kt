package tech.takahana.androidnavigationplayground.navigator.components

interface RoutePattern {
    val value: String

    operator fun invoke(): String = value
}
