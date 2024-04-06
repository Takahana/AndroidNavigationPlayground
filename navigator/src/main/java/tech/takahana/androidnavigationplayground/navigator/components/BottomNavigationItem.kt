package tech.takahana.androidnavigationplayground.navigator.components

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions

object BottomNavigationItem : ScreenLocation {
    override fun createNavOptions(
        navController: NavController,
    ) = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setRestoreState(true)
        .setPopUpTo(
            route = navController.graph.findStartDestination().route,
            inclusive = false,
            saveState = true
        )
        .build()
}