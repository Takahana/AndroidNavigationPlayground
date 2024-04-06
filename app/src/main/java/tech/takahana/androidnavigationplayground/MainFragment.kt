package tech.takahana.androidnavigationplayground

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import tech.takahana.androidnavigationplayground.navigator.NavHostFragmentScreenNavigator
import tech.takahana.androidnavigationplayground.ui.navigation.createMainBottomNavGraph
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var navHostFragmentScreenNavigatorFactory: NavHostFragmentScreenNavigator.Factory

    private val navController: NavController
        get() {
            val navHostFragment = childFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
            return navHostFragment.navController
        }
    private val screenNavigator: NavHostFragmentScreenNavigator by lazy {
        navHostFragmentScreenNavigatorFactory.create(navController, viewLifecycleOwner)
    }
    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            updateBottomNavigationItem(destination)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        setupNavGraph(bottomNavigationView)
    }

    private fun setupNavGraph(
        bottomNavigationView: BottomNavigationView,
    ) {
        navController.createMainBottomNavGraph()
        bottomNavigationView.setOnItemSelectedListener {
            val destination = MyAppScreenDestination.of(it.itemId)
                ?: return@setOnItemSelectedListener false
            try {
                screenNavigator.navigate(destination)
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
        screenNavigator.prepare()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navController.removeOnDestinationChangedListener(onDestinationChangedListener)
    }

    private fun updateBottomNavigationItem(
        destination: NavDestination,
    ) {
        val bottomNavigationView =
            view?.findViewById<BottomNavigationView>(R.id.bottom_navigation) ?: return
        bottomNavigationView.menu.forEach {
            if (destination.matchDestination(it.itemId)) it.isChecked = true
        }
    }

    private fun NavDestination.matchDestination(@IdRes destId: Int): Boolean {
        val root = MyAppScreenDestination.of(destId) ?: return false
        return hierarchy.any { it.route == root.route }
    }

    private fun MyAppScreenDestination.Companion.of(@IdRes destId: Int): MyAppScreenDestination<*>? {
        return when (destId) {
            R.id.menu_main_bottom_home -> MyAppScreenDestination.Home
            R.id.menu_main_bottom_search -> MyAppScreenDestination.Search
            else -> null
        }
    }
}