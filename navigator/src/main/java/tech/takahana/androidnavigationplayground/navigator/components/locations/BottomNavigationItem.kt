package tech.takahana.androidnavigationplayground.navigator.components.locations

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import tech.takahana.androidnavigationplayground.navigator.components.ScreenLocation

object BottomNavigationItem : ScreenLocation {
    override fun applyNavOptions(
        builder: NavOptions.Builder,
        navController: NavController,
    ) = builder
        .setLaunchSingleTop(true)
        .setRestoreState(true)
        .setPopUpTo(
            route = navController.graph.findStartDestination().route,
            inclusive = false,
            saveState = true
        )
}