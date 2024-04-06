package tech.takahana.androidnavigationplayground.navigator.components

import androidx.navigation.NavController
import androidx.navigation.NavOptions

interface ScreenLocation {

    fun createNavOptions(
        navController: NavController,
    ): NavOptions
}