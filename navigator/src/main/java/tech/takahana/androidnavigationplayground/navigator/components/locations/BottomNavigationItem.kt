package tech.takahana.androidnavigationplayground.navigator.components.locations

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import tech.takahana.androidnavigationplayground.navigator.components.ScreenLocation

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