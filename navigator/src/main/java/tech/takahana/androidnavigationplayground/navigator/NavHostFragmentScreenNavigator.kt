package tech.takahana.androidnavigationplayground.navigator

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.contains
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class NavHostFragmentScreenNavigator(
    private val dispatcher: ScreenNavigationDispatcher,
    private val navController: NavController,
    private val lifecycleOwner: LifecycleOwner,
) : ScreenNavigator {

    private var navigationRequestJob: Job? = null

    override fun navigate(destination: ScreenDestination<*>) {
        val location = destination.getLocation()
        navController.navigate(
            route = destination.route,
            navOptions = location?.createNavOptions(navController),
        )
    }

    fun prepare() {
        subscribeScreenNavigationRequest()
    }

    private fun subscribeScreenNavigationRequest() {
        navigationRequestJob?.cancel()
        navigationRequestJob = with(lifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    dispatcher.screenNavigationRequest
                        .filterNotNull()
                        .collect { request -> handleRequest(request) }
                }
            }
        }
    }

    private fun handleRequest(request: ScreenNavigationDispatcher.ScreenNavigationRequest) {
        val destination = request.destination
        if (navController.graph.contains(destination.route)) {
            navigate(destination)
            dispatcher.responded(request)
        }
    }

    class Factory(
        private val dispatcher: ScreenNavigationDispatcher,
    ) {
        fun create(
            navController: NavController,
            lifecycleOwner: LifecycleOwner,
        ): NavHostFragmentScreenNavigator {
            return NavHostFragmentScreenNavigator(
                dispatcher = dispatcher,
                navController = navController,
                lifecycleOwner = lifecycleOwner,
            )
        }
    }
}