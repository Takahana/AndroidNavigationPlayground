package tech.takahana.androidnavigationplayground.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.takahana.androidnavigationplayground.navigator.DefaultScreenNavigator
import tech.takahana.androidnavigationplayground.navigator.ScreenNavigationDispatcher
import tech.takahana.androidnavigationplayground.navigator.ScreenNavigator
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ScreenNavigationModule {

    @Singleton
    @Provides
    fun provideScreenNavigationDispatcher(): ScreenNavigationDispatcher {
        return ScreenNavigationDispatcher()
    }

    @Provides
    fun provideScreenNavigator(
        screenNavigationDispatcher: ScreenNavigationDispatcher,
    ): ScreenNavigator {
        return DefaultScreenNavigator(screenNavigationDispatcher)
    }
}