package tech.takahana.androidnavigationplayground.navigator.components

import androidx.fragment.app.DialogFragment

data class ScreenNavigationMessage(
    val requestTag: String,
    val message: Message,
) {

    sealed interface Message {
        data object ShouldCloseScreenSentRequest : Message

        data class ShouldOpenDialogFragmentOnScreenSentRequest(
            val dialogFragment: DialogFragment,
            val tag: String,
        ) : Message
    }
}