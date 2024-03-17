package tech.takahana.androidnavigationplayground.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import tech.takahana.androidnavigationplayground.HomeFragment
import tech.takahana.androidnavigationplayground.R

fun NavController.createMyAppGraph() {
    graph = createGraph(
        startDestination = R.id.destination_home.toString(),
    ) {
        fragment<HomeFragment>(route = R.id.destination_home.toString())
    }
}