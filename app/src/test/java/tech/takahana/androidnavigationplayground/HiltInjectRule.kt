package tech.takahana.androidnavigationplayground

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * from: https://github.com/DroidKaigi/conference-app-2023/blob/f255ed2f6f07f9f6f83bc3b15384b9bcf001d8e8/core/testing/src/main/java/io/github/droidkaigi/confsched2023/testing/HiltInjectRule.kt
 */
class HiltInjectRule(private val rule: HiltAndroidRule) : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        rule.inject()
    }
}