package tech.takahana.androidnavigationplayground.navigator.components

import androidx.annotation.AnimRes
import androidx.navigation.NavOptions

interface ScreenTransition {

    @get:AnimRes
    val enterAnim: Int

    @get:AnimRes
    val exitAnim: Int

    @get:AnimRes
    val popEnterAnim: Int

    @get:AnimRes
    val popExitAnim: Int


    fun applyNavOptions(builder: NavOptions.Builder): NavOptions.Builder
}