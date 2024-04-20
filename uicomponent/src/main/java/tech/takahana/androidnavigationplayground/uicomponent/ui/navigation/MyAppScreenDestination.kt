package tech.takahana.androidnavigationplayground.uicomponent.ui.navigation

import tech.takahana.androidnavigationplayground.navigator.components.BottomNavigationItem
import tech.takahana.androidnavigationplayground.navigator.components.RoutePattern
import tech.takahana.androidnavigationplayground.navigator.components.ScreenDestination
import tech.takahana.androidnavigationplayground.uicomponent.uimodel.id.TrendIdUiModel

sealed interface MyAppScreenDestination : ScreenDestination {

    data object Home : MyAppScreenDestination {

        override val route: String = "home"

        override fun getLocation() = BottomNavigationItem

        val routePattern = object : RoutePattern {
            override val value: String = "home"
        }
    }

    data object Search : MyAppScreenDestination {

        override val route: String = "search"

        override fun getLocation() = BottomNavigationItem

        val routePattern = object : RoutePattern {
            override val value: String = "search"
        }
    }

    data object Player : MyAppScreenDestination {

        override val route: String = "player"

        val routePattern: RoutePattern = object : RoutePattern {
            override val value: String = "player"
        }
    }

    data class Trend(
        val trendId: TrendIdUiModel,
    ) : MyAppScreenDestination {

        override val route: String = "trend/${trendId.value}"

        object Key {
            const val TREND_ID = "trendId"
        }

        companion object {
            val routePattern: RoutePattern = object : RoutePattern {
                override val value: String = "trend/{${Key.TREND_ID}}"
            }
        }
    }

    companion object
}