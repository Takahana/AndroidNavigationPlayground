package tech.takahana.androidnavigationplayground.navigator.ui

import android.os.Bundle
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.android.material.bottomnavigation.BottomNavigationView
import tech.takahana.androidnavigationplayground.navigator.components.ScreenDestination
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigator
import java.lang.ref.WeakReference

fun BottomNavigationView.setupWithNavController(
    navController: NavController,
    screenNavigator: ScreenNavigator,
    matchScreenDestination: (itemId: Int) -> ScreenDestination<*>?,
) {
    setOnItemSelectedListener {
        val destination = matchScreenDestination(it.itemId)
            ?: return@setOnItemSelectedListener false
        try {
            screenNavigator.navigate(destination)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    val weakReference = WeakReference(this)
    navController.addOnDestinationChangedListener(
        object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                val view = weakReference.get()
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this)
                    return
                }
                view.menu.forEach { menu ->
                    val screenDestination = matchScreenDestination(menu.itemId) ?: return@forEach
                    if (destination.hasRoute(screenDestination)) {
                        menu.isChecked = true
                    }
                }
            }

        }
    )
}

fun NavDestination.hasRoute(screenDestination: ScreenDestination<*>): Boolean {
    return hierarchy.any { it.route == screenDestination.route }
}