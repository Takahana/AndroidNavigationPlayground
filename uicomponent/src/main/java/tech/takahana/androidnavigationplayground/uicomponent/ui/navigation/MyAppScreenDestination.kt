package tech.takahana.androidnavigationplayground.uicomponent.ui.navigation

import tech.takahana.androidnavigationplayground.navigator.ScreenDestination

sealed interface MyAppScreenDestination : ScreenDestination {

    data object Player : MyAppScreenDestination {
        override val route: String = "player"
    }
}