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
import dagger.hilt.android.AndroidEntryPoint
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationMessageReceiver
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigator
import tech.takahana.androidnavigationplayground.navigator.components.applyPopAnimationsToPendingTransition
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import tech.takahana.androidnavigationplayground.uicomponent.ui.theme.AndroidNavigationPlaygroundTheme
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.PlayerIdUiModel
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel
import javax.inject.Inject

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private val playerId: PlayerIdUiModel by lazy {
        val playerId =
            requireNotNull(intent.extras?.getString(MyAppScreenDestination.Player.Key.PLAYER_ID))
        PlayerIdUiModel(playerId)
    }

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
                        playerId = playerId,
                        onClick = { navigateTo(it) }
                    )
                }
            }
        }

        ScreenNavigationMessageReceiver(
            activity = this,
            screenNavigator = screenNavigator,
        )
    }

    private fun navigateTo(destination: MyAppScreenDestination) {
        screenNavigator.navigate(
            destination,
        )
    }

    override fun finish() {
        super.finish()
        ScreenNavigator.applyPopAnimationsToPendingTransition(this)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayerScreen(
    playerId: PlayerIdUiModel,
    onClick: (MyAppScreenDestination) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("${PlayerActivity::class.simpleName}: ${playerId.value}")
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
                    Text(text = "Open Player 2")
                },
                onClick = {
                    onClick(
                        MyAppScreenDestination.Player(
                            playerId = PlayerIdUiModel("player2")
                        )
                    )
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
        item {
            PlayerContent(
                content = {
                    Text(text = "Open Purchase")
                },
                onClick = {
                    onClick(MyAppScreenDestination.Purchase)
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
                playerId = PlayerIdUiModel("player1"),
                onClick = { }
            )
        }
    }
}