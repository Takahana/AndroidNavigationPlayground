package tech.takahana.androidnavigationplayground.navigator.components

import java.util.UUID

object ScreenNavigationRequestTagCreator {

    fun createTag(): String = UUID.randomUUID().toString()
}