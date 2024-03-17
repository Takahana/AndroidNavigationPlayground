package tech.takahana.androidnavigationplayground

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import tech.takahana.androidnavigationplayground.ui.navigation.createMyAppGraph
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        setupNavGraph(bottomNavigationView, navController)
    }

    private fun setupNavGraph(
        bottomNavigationView: BottomNavigationView,
        navController: NavController,
    ) {
        navController.createMyAppGraph()
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemSelectedListener {
            val route = when (it.itemId) {
                R.id.menu_main_bottom_home -> MyAppScreenDestination.Home
                R.id.menu_main_bottom_search -> MyAppScreenDestination.Search
                else -> return@setOnItemSelectedListener false
            }
            try {
                navController.navigate(route.route())
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }
    }
}