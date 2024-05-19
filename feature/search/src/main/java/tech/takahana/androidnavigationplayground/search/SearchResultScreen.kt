package tech.takahana.androidnavigationplayground.search

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination
import tech.takahana.androidnavigationplayground.uicomponent.ui.theme.AndroidNavigationPlaygroundTheme
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.PlayerIdUiModel
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel

@Composable
fun SearchResultScreen(
    query: String,
    navigateTo: (MyAppScreenDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        SearchResultContents(
            query = query,
            onClick = navigateTo,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
internal fun SearchResultContents(
    query: String,
    onClick: (MyAppScreenDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item { Text(text = "Search result for: $query") }

        val itemModifier = Modifier.fillMaxWidth()
        item {
            SearchResultContent(
                content = {
                    Text(text = "Open Player 2")
                },
                onClick = {
                    onClick(
                        MyAppScreenDestination.Player(
                        PlayerIdUiModel("player2")
                    ))
                },
                modifier = itemModifier,
            )
        }
        item {
            SearchResultContent(
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
            SearchResultContent(
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
internal fun SearchResultContent(
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
        ,
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
@Preview
fun SearchResultScreenPreview() {
    AndroidNavigationPlaygroundTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchResultScreen(
                query = "search query",
                navigateTo = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}