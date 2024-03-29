package tech.takahana.androidnavigationplayground.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.activity
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import tech.takahana.androidnavigationplayground.home.HomeFragment
import tech.takahana.androidnavigationplayground.player.PlayerActivity
import tech.takahana.androidnavigationplayground.search.SearchFragment
import tech.takahana.androidnavigationplayground.trend.TrendFragment
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination.Home.HomeRoutePattern
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination.Player.PlayerRoutePattern
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination.Search.SearchRoutePattern
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination.Trend.TrendRoutePattern

fun NavController.createMyAppGraph() {
    graph = createGraph(
        startDestination = HomeRoutePattern()
    ) {
        fragment<HomeFragment>(route = HomeRoutePattern())
        fragment<SearchFragment>(route = SearchRoutePattern())
        activity(route = PlayerRoutePattern()) {
            this.activityClass = PlayerActivity::class
        }
        fragment<TrendFragment>(route = TrendRoutePattern())
    }
}
