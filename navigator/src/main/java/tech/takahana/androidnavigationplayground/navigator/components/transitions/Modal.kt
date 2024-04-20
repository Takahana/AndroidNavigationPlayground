package tech.takahana.androidnavigationplayground.navigator.components.transitions

import androidx.navigation.NavOptions
import tech.takahana.androidnavigationplayground.navigator.R
import tech.takahana.androidnavigationplayground.navigator.components.ScreenTransition

object Modal : ScreenTransition {
    override fun applyNavOptions(builder: NavOptions.Builder) = builder
        .setEnterAnim(R.anim.modal_enter_anim)
        .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
        .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(R.anim.modal_exit_anim)
}