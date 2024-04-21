package tech.takahana.androidnavigationplayground.navigator.components

data class ScreenNavigationMessage(
    val requestTag: String,
    val message: Message,
) {

    sealed interface Message {
        data object ShouldCloseScreenSentRequest : Message
    }
}