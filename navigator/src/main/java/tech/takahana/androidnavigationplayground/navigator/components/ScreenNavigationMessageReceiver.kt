package tech.takahana.androidnavigationplayground.navigator.components

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ScreenNavigationMessageReceiver(
    private val activity: FragmentActivity,
    private val screenNavigator: ScreenNavigator,
    private val requestTag: String,
) {

    init {
        activity.lifecycleScope.launch {
            activity.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                screenNavigator.screenNavigationMessage.onEach { message ->
                    onReceived(message, requestTag)
                }.launchIn(this)
            }
        }
    }


    private fun onReceived(
        message: ScreenNavigationMessage,
        requestTag: String,
    ) {
        if (message.requestTag != requestTag) return
        when (message.message) {
            ScreenNavigationMessage.Message.ShouldCloseScreenSentRequest -> {
                activity.finish()
                screenNavigator.respondedTo(message)
            }
        }
    }
}