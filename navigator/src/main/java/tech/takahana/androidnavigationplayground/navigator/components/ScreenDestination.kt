package tech.takahana.androidnavigationplayground.navigator.components

import android.os.Bundle

interface ScreenDestination {

    val route: String

    val location: ScreenLocation?
        get() = null

    val transition: ScreenTransition?
        get() = null

    fun getArgs(): Bundle = Bundle()
}