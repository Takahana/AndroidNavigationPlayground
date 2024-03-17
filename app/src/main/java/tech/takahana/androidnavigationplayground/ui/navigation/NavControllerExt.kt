package tech.takahana.androidnavigationplayground.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import tech.takahana.androidnavigationplayground.R
import tech.takahana.androidnavigationplayground.home.HomeFragment

fun NavController.createMyAppGraph() {
    graph = createGraph(
        startDestination = R.id.destination_home.toString(),
    ) {
        fragment<HomeFragment>(route = R.id.destination_home.toString())
    }
}