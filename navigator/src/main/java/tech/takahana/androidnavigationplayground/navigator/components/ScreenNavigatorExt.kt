package tech.takahana.androidnavigationplayground.navigator.components

import android.app.Activity
import androidx.navigation.ActivityNavigator

fun ScreenNavigator.Companion.applyPopAnimationsToPendingTransition(activity: Activity) {
    ActivityNavigator.applyPopAnimationsToPendingTransition(activity)
}