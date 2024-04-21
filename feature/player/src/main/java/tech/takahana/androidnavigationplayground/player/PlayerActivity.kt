package tech.takahana.androidnavigationplayground.player

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationMessage
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigator
import tech.takahana.androidnavigationplayground.navigator.components.applyPopAnimationsToPendingTransition
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import tech.takahana.androidnavigationplayground.uicomponent.ui.theme.AndroidNavigationPlaygroundTheme
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel
import javax.inject.Inject

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidNavigationPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlayerScreen(
                        onClick = { navigateTo(it) }
                    )
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                screenNavigator.screenNavigationMessage.onEach { message ->
                    onReceivedNavMessage(message)
                }.launchIn(this)
            }
        }
    }

    private fun navigateTo(destination: MyAppScreenDestination) {
        screenNavigator.navigate(
            destination,
            NAV_REQUEST_TAG,
        )
    }

    override fun finish() {
        super.finish()
        ScreenNavigator.applyPopAnimationsToPendingTransition(this)
    }

    private fun onReceivedNavMessage(message: ScreenNavigationMessage) {
        if (message.requestTag != NAV_REQUEST_TAG) return
        when (message.message) {
            ScreenNavigationMessage.Message.ShouldCloseScreenSentRequest -> {
                finish()
                screenNavigator.respondedTo(message)
            }
        }
    }

    companion object {
        private const val NAV_REQUEST_TAG = "NAV_REQUEST_TAG_ON_PLAYER_ACTIVITY"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayerScreen(
    onClick: (MyAppScreenDestination) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("${PlayerActivity::class.simpleName}")
                }
            )
        },
    ) { innerPadding ->
        PlayerContents(
            modifier = Modifier.padding(innerPadding),
            onClick = onClick,
        )
    }
}

@Composable
internal fun PlayerContents(
    onClick: (MyAppScreenDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val itemModifier = Modifier.fillMaxWidth()
        item {
            PlayerContent(
                content = {
                    Text(text = "Open Player")
                },
                onClick = {
                    onClick(MyAppScreenDestination.Player)
                },
                modifier = itemModifier,
            )
        }
        item {
            PlayerContent(
                content = {
                    Text(text = "Open Trend 2")
                },
                onClick = {
                    onClick(MyAppScreenDestination.Trend(TrendIdUiModel("trend2")))
                },
                modifier = itemModifier,
            )
        }
    }
}

@Composable
internal fun PlayerContent(
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Preview
@Composable
internal fun PlayerScreenPreview()  {
    AndroidNavigationPlaygroundTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PlayerScreen(
                onClick = { }
            )
        }
    }
}