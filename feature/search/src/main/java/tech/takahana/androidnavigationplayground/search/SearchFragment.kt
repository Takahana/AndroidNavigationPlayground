package tech.takahana.androidnavigationplayground.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.takahana.androidnavigationplayground.navigator.components.ComposeScreenNavigator
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import tech.takahana.androidnavigationplayground.uicomponent.ui.theme.AndroidNavigationPlaygroundTheme
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject
    lateinit var composeScreenNavigatorFactory: ComposeScreenNavigator.Factory

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
                        val navController: NavHostController = rememberNavController()
                        val screenNavigator = composeScreenNavigatorFactory.create(navController)
                        SearchDisplay(
                            navController = navController,
                            navigateTo = screenNavigator::navigate,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchDisplay(
    navController: NavHostController,
    navigateTo: (MyAppScreenDestination) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") }
            )
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = MyAppScreenDestination.Search.SearchRoutePattern.value,
        ) {
            composable(MyAppScreenDestination.Search.SearchRoutePattern.value) {
                SearchTopScreen(
                    navigateTo = navigateTo,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

    }
}

@Preview
@Composable
internal fun SearchScreenPreview() {
    AndroidNavigationPlaygroundTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchDisplay(
                navController = rememberNavController(),
                navigateTo = {}
            )
        }
    }
}
