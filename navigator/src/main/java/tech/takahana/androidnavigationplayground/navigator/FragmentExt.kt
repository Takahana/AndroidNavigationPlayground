package tech.takahana.androidnavigationplayground.navigator

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.findScreenNavigator(): ScreenNavigator =
    NavHostFragmentScreenNavigator(findNavController())
