package tech.takahana.androidnavigationplayground.navigator.components

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigator.Companion.KEY_NAVIGATED_NOT_ON_GRAPH
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigator.Companion.KEY_POP_ENTER_ANIM
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigator.Companion.KEY_POP_EXIT_ANIM

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
            }

            is ScreenNavigationMessage.Message.ShouldOpenDialogFragmentOnScreenSentRequest -> {
                val dialogFragment = message.message.dialogFragment
                dialogFragment.show(
                    activity.supportFragmentManager,
                    message.message.tag,
                )
            }

            is ScreenNavigationMessage.Message.ShouldStartActivityOnScreenSentRequest -> {
                val intent = Intent(activity, Class.forName(message.message.className)).apply {
                    putExtras(
                        bundleOf(
                            KEY_NAVIGATED_NOT_ON_GRAPH to true,
                            KEY_POP_ENTER_ANIM to message.message.popEnterAnim,
                            KEY_POP_EXIT_ANIM to message.message.popExitAnim,
                        )
                    )
                }
                activity.startActivity(intent)

                val enterAnim = message.message.enterAnim
                val exitAnim = message.message.exitAnim
                if (enterAnim != null && exitAnim != null) {
                    activity.overridePendingTransition(
                        enterAnim,
                        exitAnim,
                    )
                }
            }
        }
        screenNavigator.respondedTo(message)
    }
}