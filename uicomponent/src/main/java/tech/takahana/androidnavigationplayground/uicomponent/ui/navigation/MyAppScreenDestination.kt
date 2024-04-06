package tech.takahana.androidnavigationplayground.uicomponent.ui.navigation

import tech.takahana.androidnavigationplayground.navigator.components.BottomNavigationItem
import tech.takahana.androidnavigationplayground.navigator.components.ScreenDestination
import tech.takahana.androidnavigationplayground.navigator.components.ScreenDestination.RoutePattern
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel

sealed interface MyAppScreenDestination<T: RoutePattern> : ScreenDestination<T> {

    data object Home : MyAppScreenDestination<Home.HomeRoutePattern> {

        override val route: String = HomeRoutePattern.value

        override fun getLocation() = BottomNavigationItem

        object HomeRoutePattern : RoutePattern {
            override val value: String = "home"
        }
    }

    data object Search : MyAppScreenDestination<Search.SearchRoutePattern> {

        override val route: String = SearchRoutePattern.value

        override fun getLocation() = BottomNavigationItem

        object SearchRoutePattern : RoutePattern {
            override val value: String = "search"
        }
    }

    data object Player : MyAppScreenDestination<Player.PlayerRoutePattern> {

        override val route: String = PlayerRoutePattern.value

        object PlayerRoutePattern : RoutePattern {
            override val value: String = "player"
        }
    }

    data class Trend(
        val trendId: TrendIdUiModel,
    ) : MyAppScreenDestination<Trend.TrendRoutePattern> {

        override val route: String = "trend/${trendId.value}"

        object TrendRoutePattern : RoutePattern {
            override val value: String = "trend/{${Key.TREND_ID}}"
        }

        object Key {
            const val TREND_ID = "trendId"
        }
    }

    companion object
}