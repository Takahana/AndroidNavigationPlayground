package tech.takahana.androidnavigationplayground.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationMessageDispatcher
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationRequestDispatcher
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ScreenNavigationDispatcherModule::class]
)
@Module
class TestScreenNavigationDispatcherModule {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Singleton
    @Provides
    fun provideScreenNavigationRequestDispatcher(): ScreenNavigationRequestDispatcher {
        return ScreenNavigationRequestDispatcher(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Singleton
    @Provides
    fun provideScreenNavigationMessageDispatcher(): ScreenNavigationMessageDispatcher {
        return ScreenNavigationMessageDispatcher(UnconfinedTestDispatcher())
    }
}