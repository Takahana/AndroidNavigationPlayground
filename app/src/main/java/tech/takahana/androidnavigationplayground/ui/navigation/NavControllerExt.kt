package tech.takahana.androidnavigationplayground.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.activity
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import tech.takahana.androidnavigationplayground.MainFragment
import tech.takahana.androidnavigationplayground.home.HomeFragment
import tech.takahana.androidnavigationplayground.player.PlayerActivity
import tech.takahana.androidnavigationplayground.search.SearchFragment
import tech.takahana.androidnavigationplayground.trend.TrendFragment
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination.Home
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination.Player
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination.Search
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination.Trend

private const val MainNavGraph = "main_nav_graph"

fun NavController.createMainNavGraph() {
    graph = createGraph(
        startDestination = MainNavGraph,
    ) {
        fragment<MainFragment>(route = MainNavGraph)
    }
}

fun NavController.createMainBottomNavGraph() {
    graph = createGraph(
        startDestination = Home.route
    ) {
        fragment<HomeFragment>(route = Home.routePattern.value)
        fragment<SearchFragment>(route = Search.routePattern.value)
        activity(route = Player.routePattern.value) {
            this.activityClass = PlayerActivity::class
        }
        fragment<TrendFragment>(route = Trend.routePattern.value)
    }
}
