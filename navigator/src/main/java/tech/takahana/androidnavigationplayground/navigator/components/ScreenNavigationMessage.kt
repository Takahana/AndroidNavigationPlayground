package tech.takahana.androidnavigationplayground.navigator.components

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.fragment.app.DialogFragment

data class ScreenNavigationMessage(
    val tag: String,
    val message: Message,
) {

    sealed interface Message {
        data class ShouldOpenDialogFragmentOnScreenSentRequest(
            val dialogFragment: DialogFragment,
            val tag: String,
            val args: Bundle,
        ) : Message

        data class ShouldStartActivityOnScreenSentRequest(
            val className: String,
            @AnimRes val enterAnim: Int?,
            @AnimRes val exitAnim: Int?,
            @AnimRes val popEnterAnim: Int?,
            @AnimRes val popExitAnim: Int?,
            val args: Bundle,
        ) : Message
    }
}