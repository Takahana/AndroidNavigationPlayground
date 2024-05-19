package tech.takahana.androidnavigationplayground.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationMessageReceiver
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigator
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import tech.takahana.androidnavigationplayground.uicomponent.ui.theme.AndroidNavigationPlaygroundTheme
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.PlayerIdUiModel
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel
import javax.inject.Inject

@AndroidEntryPoint
class PurchaseFragment : Fragment() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ScreenNavigationMessageReceiver(
            activity = requireActivity(),
            screenNavigator = screenNavigator,
            requestTag = NAV_REQUEST_TAG,
        )
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AndroidNavigationPlaygroundTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        PurchaseScreen(
                            onClick = { navigateTo(it) }
                        )
                    }
                }
            }
        }
    }

    private fun navigateTo(destination: MyAppScreenDestination) {
        screenNavigator.navigate(destination, NAV_REQUEST_TAG)
    }

    companion object {
        private const val NAV_REQUEST_TAG = "nav_request_tag_from_purchase"
    }
}

@Composable
internal fun PurchaseScreen(
    onClick: (MyAppScreenDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        PurchaseContents(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
internal fun PurchaseContents(
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
            PurchaseContent(
                content = {
                    Text(text = "Open Player 3")
                },
                onClick = {
                    onClick(MyAppScreenDestination.Player(
                        PlayerIdUiModel("player3")
                    ))
                },
                modifier = itemModifier,
            )
        }
        item {
            PurchaseContent(
                content = {
                    Text(text = "Open Trend 1")
                },
                onClick = {
                    onClick(MyAppScreenDestination.Trend(TrendIdUiModel("trend1")))
                },
                modifier = itemModifier,
            )
        }
        item {
            PurchaseContent(
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
internal fun PurchaseContent(
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
internal fun PurchaseScreenPreview() {
    AndroidNavigationPlaygroundTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PurchaseScreen(
                onClick = { }
            )
        }
    }
}