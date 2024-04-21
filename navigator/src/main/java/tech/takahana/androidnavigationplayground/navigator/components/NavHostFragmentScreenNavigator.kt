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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class NavHostFragmentScreenNavigator(
    private val navigationMessageDispatcher: ScreenNavigationMessageDispatcher,
    private val navigationRequestDispatcher: ScreenNavigationRequestDispatcher,
    private val navController: NavController,
    private val lifecycleOwner: LifecycleOwner,
    private val activity: WeakReference<Activity>
) : ScreenNavigator {

    private var navigationRequestJob: Job? = null

    override val screenNavigationMessage: Flow<ScreenNavigationMessage> =
        navigationMessageDispatcher.screenNavigationMessage.filterNotNull()

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

    override fun navigate(
        destination: ScreenDestination,
        requestTag: String?,
    ) = throw UnsupportedOperationException("requestTag is not supported.")

    override fun respondedTo(message: ScreenNavigationMessage) {
        navigationMessageDispatcher.responded(message)
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
                navigationRequestDispatcher.screenNavigationRequest
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
                navigationRequestDispatcher.responded(request)
            } else {
                if (request.requestTag != null) {
                    navigationMessageDispatcher.dispatch(
                        ScreenNavigationMessage(
                            requestTag = request.requestTag,
                            message = ScreenNavigationMessage.Message.ShouldCloseScreenSentRequest,
                        )
                    )
                }
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
        private val navigationMessageDispatcher: ScreenNavigationMessageDispatcher,
        private val navigationRequestDispatcher: ScreenNavigationRequestDispatcher,
    ) {
        fun create(
            navController: NavController,
            lifecycleOwner: LifecycleOwner,
            activity: Activity,
        ): NavHostFragmentScreenNavigator {
            return NavHostFragmentScreenNavigator(
                navigationMessageDispatcher = navigationMessageDispatcher,
                navigationRequestDispatcher = navigationRequestDispatcher,
                navController = navController,
                lifecycleOwner = lifecycleOwner,
                activity = WeakReference(activity),
            )
        }
    }
}