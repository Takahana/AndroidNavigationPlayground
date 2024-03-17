package tech.takahana.androidnavigationplayground.ui.navigation

import android.content.Context
import android.content.res.Resources
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import tech.takahana.androidnavigationplayground.R
import tech.takahana.androidnavigationplayground.home.HomeFragment
import tech.takahana.androidnavigationplayground.search.SearchFragment

fun NavController.createMyAppGraph(
    context: Context,
) {
    graph = createGraph(
        startDestination = R.id.destination_home.toRoute(context),
    ) {
        fragment<HomeFragment>(route = R.id.destination_home.toRoute(context))
        fragment<SearchFragment>(route = R.id.destination_search.toRoute(context))
    }
}

fun @receiver:IdRes Int.toRoute(context: Context): String {
    return try {
        context.resources.getResourceName(this)
    } catch (e: Resources.NotFoundException) {
        toString()
    }
}