package tech.takahana.androidnavigationplayground.navigator.components

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.contains
import androidx.navigation.fragment.DialogFragmentNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationMessage.Message.ShouldOpenDialogFragmentOnScreenSentRequest
import tech.takahana.androidnavigationplayground.navigator.components.transitions.Modal
import java.lang.ref.WeakReference

class NavHostFragmentScreenNavigator(
    private val navigationMessageDispatcher: ScreenNavigationMessageDispatcher,
    private val navigationRequestDispatcher: ScreenNavigationRequestDispatcher,
    private val navController: NavController,
    private val lifecycleOwner: LifecycleOwner,
    private val activity: WeakReference<Activity>,
    private val navHostFragmentManager: FragmentManager,
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
                dispatchMessage(request)
            }
        }
    }

    private fun dispatchMessage(
        request: ScreenNavigationRequest,
    ) {
        when (request.destination.transition) {
            is Modal -> dispatchOpenScreenMessage(request)
            else -> {
                dispatchCloseScreenMessage(request)
                moveToForeground()
            }
        }
    }

    private fun dispatchOpenScreenMessage(
        request: ScreenNavigationRequest,
    ) {
        when (val node = navController.graph.findNode(request.destination.route)) {
            is DialogFragmentNavigator.Destination -> {
                val dialogFragment = createDialogFragment(node.className)
                if (dialogFragment != null && request.requestTag != null) {
                    navigationMessageDispatcher.dispatch(
                        ScreenNavigationMessage(
                            requestTag = request.requestTag,
                            message = ShouldOpenDialogFragmentOnScreenSentRequest(
                                dialogFragment = dialogFragment,
                                tag = request.requestTag,
                            ),
                        )
                    )
                }
            }
        }
        navigationRequestDispatcher.responded(request)
    }

    private fun dispatchCloseScreenMessage(
        request: ScreenNavigationRequest,
    ) {
        navigationMessageDispatcher.dispatch(
            ScreenNavigationMessage(
                requestTag = request.requestTag ?: return,
                message = ScreenNavigationMessage.Message.ShouldCloseScreenSentRequest,
            )
        )
    }

    private fun moveToForeground() {
        val activity = activity.get() ?: return
        val intent = Intent(activity, activity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        activity.startActivity(intent)
    }

    private fun createDialogFragment(
        className: String,
    ): DialogFragment? {
        val activity = activity.get() ?: return null
        val classLoader = activity.classLoader
        return navHostFragmentManager.fragmentFactory.instantiate(
            classLoader,
            className
        ) as? DialogFragment
    }

    class Factory(
        private val navigationMessageDispatcher: ScreenNavigationMessageDispatcher,
        private val navigationRequestDispatcher: ScreenNavigationRequestDispatcher,
    ) {
        fun create(
            navController: NavController,
            lifecycleOwner: LifecycleOwner,
            activity: Activity,
            navHostFragmentManager: FragmentManager,
        ): NavHostFragmentScreenNavigator {
            return NavHostFragmentScreenNavigator(
                navigationMessageDispatcher = navigationMessageDispatcher,
                navigationRequestDispatcher = navigationRequestDispatcher,
                navController = navController,
                lifecycleOwner = lifecycleOwner,
                activity = WeakReference(activity),
                navHostFragmentManager = navHostFragmentManager,
            )
        }
    }
}