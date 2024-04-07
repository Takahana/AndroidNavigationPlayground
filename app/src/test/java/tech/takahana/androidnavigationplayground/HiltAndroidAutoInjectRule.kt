package tech.takahana.androidnavigationplayground

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * from: https://github.com/DroidKaigi/conference-app-2023/blob/f255ed2f6f07f9f6f83bc3b15384b9bcf001d8e8/core/testing/src/main/java/io/github/droidkaigi/confsched2023/testing/HiltAndroidAutoInjectRule.kt
 */
class HiltAndroidAutoInjectRule(
    private val testInstance: Any,
) : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        val hiltAndroidRule = HiltAndroidRule(testInstance)
        return RuleChain
            .outerRule(hiltAndroidRule)
            .around(HiltInjectRule(hiltAndroidRule))
            .apply(base, description)
    }
}