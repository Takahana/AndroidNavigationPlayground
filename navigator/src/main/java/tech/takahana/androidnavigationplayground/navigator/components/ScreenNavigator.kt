package tech.takahana.androidnavigationplayground.navigator.components

import kotlinx.coroutines.flow.Flow

interface ScreenNavigator {

    val screenNavigationMessage: Flow<ScreenNavigationMessage>

    @Throws(IllegalArgumentException::class)
    fun navigate(destination: ScreenDestination)

    @Throws(IllegalArgumentException::class)
    fun navigate(
        destination: ScreenDestination,
        requestTag: String?,
    )

    fun respondedTo(message: ScreenNavigationMessage)

    companion object
}