package tech.takahana.androidnavigationplayground.navigator

import androidx.navigation.NavController

class FragmentScreenNavigator(
    private val navController: NavController,
) : ScreenNavigator {

    override fun navigate(destination: ScreenDestination<*>) {
        navController.navigate(destination.route())
    }
}