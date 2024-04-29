package tech.takahana.androidnavigationplayground.navigator.components.transitions

import androidx.navigation.NavOptions
import tech.takahana.androidnavigationplayground.navigator.R
import tech.takahana.androidnavigationplayground.navigator.components.ScreenTransition

object Modal : ScreenTransition {
    override val enterAnim: Int
        get() = R.anim.modal_enter_anim
    override val exitAnim: Int
        get() = androidx.navigation.ui.R.anim.nav_default_exit_anim
    override val popEnterAnim: Int
        get() = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim
    override val popExitAnim: Int
        get() = R.anim.modal_exit_anim

    override fun applyNavOptions(builder: NavOptions.Builder) = builder
        .setEnterAnim(enterAnim)
        .setExitAnim(exitAnim)
        .setPopEnterAnim(popEnterAnim)
        .setPopExitAnim(popExitAnim)
}