package tech.takahana.androidnavigationplayground.trend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import tech.takahana.androidnavigationplayground.uicomponent.ui.theme.AndroidNavigationPlaygroundTheme
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination.Trend as TrendDestination

class TrendFragment : Fragment() {

    private val trendId: TrendIdUiModel by lazy {
        val trendId = requireArguments().getString(TrendDestination.Key.TREND_ID)
        TrendIdUiModel(
            requireNotNull(trendId)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AndroidNavigationPlaygroundTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        TrendScreen(
                            trendId = trendId,
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun TrendScreen(
    trendId: TrendIdUiModel,
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Text(text = trendId.value)
    }
}

@Preview
@Composable
internal fun TrendScreenPreview() {
    AndroidNavigationPlaygroundTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TrendScreen(
                trendId = TrendIdUiModel("trend1")
            )
        }
    }
}
