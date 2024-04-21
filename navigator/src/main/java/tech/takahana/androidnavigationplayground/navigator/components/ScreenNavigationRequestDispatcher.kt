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

class ScreenNavigationRequestDispatcher(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val coroutineScope: CoroutineScope = CoroutineScope(dispatcher + SupervisorJob()),
) {

    private val requestDeque = ArrayDeque<ScreenNavigationRequest>()
    private val mutex = Mutex()

    private val mutableScreenNavigationRequest = MutableStateFlow<ScreenNavigationRequest?>(null)
    val screenNavigationRequest = mutableScreenNavigationRequest.asStateFlow()

    fun dispatch(
        destination: ScreenDestination,
        requestTag: String?,
    ) = updateRequestDeque {
        addLast(ScreenNavigationRequest(destination, requestTag))
    }

    fun responded(request: ScreenNavigationRequest) =
        updateRequestDeque {
            remove(request)
        }

    private fun updateRequestDeque(
        block: ArrayDeque<ScreenNavigationRequest>.() -> Unit,
    ) {
        coroutineScope.launch {
            mutex.withLock {
                requestDeque.block()
                mutableScreenNavigationRequest.value = requestDeque.firstOrNull()
            }
        }
    }
}