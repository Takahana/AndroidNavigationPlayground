package tech.takahana.androidnavigationplayground

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import tech.takahana.androidnavigationplayground.navigator.components.NavHostFragmentScreenNavigator
import tech.takahana.androidnavigationplayground.navigator.ui.setupWithNavController
import tech.takahana.androidnavigationplayground.ui.navigation.createMainBottomNavGraph
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var navHostFragmentScreenNavigatorFactory: NavHostFragmentScreenNavigator.Factory

    @VisibleForTesting
    val navHostFragment: NavHostFragment
        get() = childFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment

    private val navController: NavController
        get() = navHostFragment.navController

    private val screenNavigator: NavHostFragmentScreenNavigator by lazy {
        navHostFragmentScreenNavigatorFactory.create(
            navController,
            viewLifecycleOwner,
            requireActivity()
        )
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
        bottomNavigationView.setupWithNavController(
            navController = navController,
            screenNavigator = screenNavigator,
            matchScreenDestination = { itemId -> MyAppScreenDestination.of(itemId) }
        )
    }

    private fun MyAppScreenDestination.Companion.of(@IdRes itemId: Int): MyAppScreenDestination? {
        return when (itemId) {
            R.id.menu_main_bottom_home -> MyAppScreenDestination.Home
            R.id.menu_main_bottom_search -> MyAppScreenDestination.Search
            else -> null
        }
    }
}