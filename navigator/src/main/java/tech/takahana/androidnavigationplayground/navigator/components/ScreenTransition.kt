package tech.takahana.androidnavigationplayground.navigator.components

import androidx.navigation.NavOptions

interface ScreenTransition {

    fun applyNavOptions(builder: NavOptions.Builder): NavOptions.Builder
}