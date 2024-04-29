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

    companion object {
        internal const val KEY_NAVIGATED_NOT_ON_GRAPH = "key_navigated_not_on_graph"
        internal const val KEY_POP_ENTER_ANIM = "key_pop_enter_anim"
        internal const val KEY_POP_EXIT_ANIM = "key_pop_exit_anim"
    }
}