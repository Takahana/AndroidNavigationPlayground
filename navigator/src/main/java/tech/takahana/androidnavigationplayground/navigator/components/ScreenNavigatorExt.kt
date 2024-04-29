package tech.takahana.androidnavigationplayground.navigator.components

import android.app.Activity
import androidx.navigation.ActivityNavigator

fun ScreenNavigator.Companion.applyPopAnimationsToPendingTransition(activity: Activity) {
    val intent = activity.intent
    val isNavigatedNotOnGraph = intent.getBooleanExtra(KEY_NAVIGATED_NOT_ON_GRAPH, false)
    if (isNavigatedNotOnGraph) {
        val popEnterAnim = intent.getIntExtra(KEY_POP_ENTER_ANIM, -1)
        val popExitAnim = intent.getIntExtra(KEY_POP_EXIT_ANIM, -1)

        if (popEnterAnim == -1 || popExitAnim == -1) return
        activity.overridePendingTransition(popEnterAnim, popExitAnim)
    } else {
        ActivityNavigator.applyPopAnimationsToPendingTransition(activity)
    }
}