package tech.takahana.androidnavigationplayground.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.takahana.androidnavigationplayground.navigator.components.DefaultScreenNavigator
import tech.takahana.androidnavigationplayground.navigator.components.NavHostFragmentScreenNavigator
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationDispatcher
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigator
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

    @Provides
    fun provideNavHostFragmentScreenNavigatorFactory(
        screenNavigationDispatcher: ScreenNavigationDispatcher,
    ): NavHostFragmentScreenNavigator.Factory {
        return NavHostFragmentScreenNavigator.Factory(screenNavigationDispatcher)
    }
}