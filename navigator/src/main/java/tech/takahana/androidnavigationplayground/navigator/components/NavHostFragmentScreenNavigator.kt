package tech.takahana.androidnavigationplayground.navigator.components

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.contains
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class NavHostFragmentScreenNavigator(
    private val dispatcher: ScreenNavigationRequestDispatcher,
    private val navController: NavController,
    private val lifecycleOwner: LifecycleOwner,
    private val activity: WeakReference<Activity>
) : ScreenNavigator {

    private var navigationRequestJob: Job? = null

    override fun navigate(destination: ScreenDestination) {
        navController.navigate(
            route = destination.route,
            navOptions = NavOptions.Builder()
                .apply {
                    destination.location?.applyNavOptions(this, navController)
                    destination.transition?.applyNavOptions(this)
                }
                .build(),
        )
    }

    init {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                subscribeScreenNavigationRequest()
            }
        })
    }

    private fun subscribeScreenNavigationRequest() {
        navigationRequestJob?.cancel()
        navigationRequestJob = with(lifecycleOwner) {
            lifecycleScope.launch {
                dispatcher.screenNavigationRequest
                    .filterNotNull()
                    .collect { request -> handleRequest(request) }
            }
        }
    }

    private fun handleRequest(request: ScreenNavigationRequest) {
        val destination = request.destination
        if (navController.graph.contains(destination.route)) {
            if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                navigate(destination)
                dispatcher.responded(request)
            } else {
                moveToForeground()
            }
        }
    }

    private fun moveToForeground() {
        val activity = activity.get() ?: return
        val intent = Intent(activity, activity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        activity.startActivity(intent)
    }
    class Factory(
        private val dispatcher: ScreenNavigationRequestDispatcher,
    ) {
        fun create(
            navController: NavController,
            lifecycleOwner: LifecycleOwner,
            activity: Activity,
        ): NavHostFragmentScreenNavigator {
            return NavHostFragmentScreenNavigator(
                dispatcher = dispatcher,
                navController = navController,
                lifecycleOwner = lifecycleOwner,
                activity = WeakReference(activity),
            )
        }
    }
}