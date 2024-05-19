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
fun SearchTopScreen(
    navigateTo: (MyAppScreenDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        SearchTopContents(
            onClick = navigateTo,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
internal fun SearchTopContents(
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
            SearchTopContent(
                content = {
                    Text(text = "Open Player 1")
                },
                onClick = {
                    onClick(
                        MyAppScreenDestination.Player(
                        PlayerIdUiModel("player1")
                    ))
                },
                modifier = itemModifier,
            )
        }
        item {
            SearchTopContent(
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
            SearchTopContent(
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
internal fun SearchTopContent(
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
fun SearchTopScreenPreview() {
    AndroidNavigationPlaygroundTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchTopScreen(
                navigateTo = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}