package tech.takahana.androidnavigationplayground.ui.navigation

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.activity
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import tech.takahana.androidnavigationplayground.R
import tech.takahana.androidnavigationplayground.home.HomeFragment
import tech.takahana.androidnavigationplayground.player.PlayerActivity
import tech.takahana.androidnavigationplayground.search.SearchFragment
import tech.takahana.androidnavigationplayground.trend.TrendFragment
import tech.takahana.androidnavigationplayground.uicomponent.ui.navigation.MyAppScreenDestination

fun NavController.createMyAppGraph() {
    graph = createGraph(
        startDestination = R.id.destination_home.toRoute()
    ) {
        fragment<HomeFragment>(route = R.id.destination_home.toRoute())
        fragment<SearchFragment>(route = R.id.destination_search.toRoute())
        activity(route = MyAppScreenDestination.Player.PlayerRoutePattern.value) {
            this.activityClass = PlayerActivity::class
        }
        fragment<TrendFragment>(route = MyAppScreenDestination.Trend.TrendRoutePattern.value)
    }
}

fun @receiver:IdRes Int.toRoute(): String {
    return toString()
}