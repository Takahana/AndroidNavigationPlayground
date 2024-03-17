package tech.takahana.androidnavigationplayground.uicomponent.ui.navigation

import tech.takahana.androidnavigationplayground.navigator.ScreenDestination
import tech.takahana.androidnavigationplayground.navigator.ScreenDestination.RoutePattern

sealed interface MyAppScreenDestination<T: RoutePattern> : ScreenDestination<T> {

    data object Home : MyAppScreenDestination<Home.HomeRoutePattern> {
        override fun route(): String = HomeRoutePattern.value

        object HomeRoutePattern : RoutePattern {
            override val value: String = "home"
        }
    }

    data object Search : MyAppScreenDestination<Search.SearchRoutePattern> {
        override fun route(): String = SearchRoutePattern.value

        object SearchRoutePattern : RoutePattern {
            override val value: String = "search"
        }
    }

    data object Player : MyAppScreenDestination<Player.PlayerRoutePattern> {

        override fun route(): String = PlayerRoutePattern.value

        object PlayerRoutePattern : RoutePattern {
            override val value: String = "player"
        }
    }

    data class Trend(val id: String) : MyAppScreenDestination<Trend.TrendRoutePattern> {
        override fun route(): String = "trend/$id"

        object TrendRoutePattern : RoutePattern {
            override val value: String = "trend/{id}"
        }
    }
}