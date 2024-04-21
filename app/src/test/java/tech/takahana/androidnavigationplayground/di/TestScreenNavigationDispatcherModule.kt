package tech.takahana.androidnavigationplayground.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
    fun provideScreenNavigationDispatcher(): ScreenNavigationRequestDispatcher {
        return ScreenNavigationRequestDispatcher(UnconfinedTestDispatcher())
    }
}