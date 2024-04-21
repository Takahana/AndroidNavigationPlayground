package tech.takahana.androidnavigationplayground.navigator.components

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ScreenNavigationMessageDispatcher(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val coroutineScope: CoroutineScope = CoroutineScope(dispatcher + SupervisorJob()),
) {

    private val messageDeque = ArrayDeque<ScreenNavigationMessage>()
    private val mutex = Mutex()

    private val mutableScreenNavigationMessage = MutableStateFlow<ScreenNavigationMessage?>(null)
    val screenNavigationMessage = mutableScreenNavigationMessage.asStateFlow()

    fun dispatch(message: ScreenNavigationMessage) =
        updateMessageDeque {
            addLast(message)
        }

    fun responded(message: ScreenNavigationMessage) =
        updateMessageDeque {
            remove(message)
        }

    private fun updateMessageDeque(
        block: ArrayDeque<ScreenNavigationMessage>.() -> Unit,
    ) {
        coroutineScope.launch {
            mutex.withLock {
                messageDeque.block()
                mutableScreenNavigationMessage.value = messageDeque.firstOrNull()
            }
        }
    }
}