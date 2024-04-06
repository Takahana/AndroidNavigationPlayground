package tech.takahana.androidnavigationplayground.navigator

import androidx.navigation.NavController

class NavHostFragmentScreenNavigator(
    private val navController: NavController,
) : ScreenNavigator {

    override fun navigate(destination: ScreenDestination<*>) {
        val location = destination.getLocation()
        navController.navigate(
            route = destination.route,
            navOptions = location?.createNavOptions(navController),
        )
    }
}