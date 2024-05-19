package tech.takahana.androidnavigationplayground.uicomponent.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import tech.takahana.androidnavigationplayground.navigator.components.RoutePattern
import tech.takahana.androidnavigationplayground.navigator.components.ScreenDestination
import tech.takahana.androidnavigationplayground.navigator.components.locations.BottomNavigationItem
import tech.takahana.androidnavigationplayground.navigator.components.transitions.Modal
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.PlayerIdUiModel
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel

sealed interface MyAppScreenDestination : ScreenDestination {

    data object Home : MyAppScreenDestination {

        override val route: String = "home"

        override val location = BottomNavigationItem

        object HomeRoutePattern : RoutePattern {
            override val value: String = "home"
        }
    }

    data object SearchTop : MyAppScreenDestination {

        override val route: String = "search"

        override val location = BottomNavigationItem

        object SearchTopRoutePattern : RoutePattern {
            override val value: String = "search"
        }
    }

    data class SearchResult(
        val query: String,
    ) : MyAppScreenDestination {

        override val route: String = "search/result?${Key.QUERY}=$query"

        override fun getArgs(): Bundle = bundleOf(
            Key.QUERY to query
        )

        object Key {
            const val QUERY = "query"
        }


        object SearchResultRoutePattern : RoutePattern {
            override val value: String = "search/result?${Key.QUERY}={${Key.QUERY}}"
        }
    }

    data class Player(
        val playerId: PlayerIdUiModel,
    ) : MyAppScreenDestination {

        override val route: String = "player/${playerId.value}"

        override val transition = Modal
        override fun getArgs(): Bundle = bundleOf(Key.PLAYER_ID to playerId.value)

        object Key {
            const val PLAYER_ID = "playerId"
        }

        object PlayerRoutePattern : RoutePattern {
            override val value: String = "player/{${Key.PLAYER_ID}}"
        }
    }

    data class Trend(
        val trendId: TrendIdUiModel,
    ) : MyAppScreenDestination {

        override val route: String = "trend/${trendId.value}"

        override fun getArgs(): Bundle = bundleOf(Key.TREND_ID to trendId.value)

        object Key {
            const val TREND_ID = "trendId"
        }

        object TrendRoutePattern : RoutePattern {
            override val value: String = "trend/{${Key.TREND_ID}}"
        }
    }

    data object Purchase : MyAppScreenDestination {

        override val route: String = "purchase"

        override val transition = Modal

        object PurchaseRoutePattern : RoutePattern {
            override val value: String = "purchase"
        }
    }

    companion object
}