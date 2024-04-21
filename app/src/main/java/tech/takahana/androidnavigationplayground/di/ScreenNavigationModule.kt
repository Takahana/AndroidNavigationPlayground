package tech.takahana.androidnavigationplayground.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import tech.takahana.androidnavigationplayground.navigator.components.DefaultScreenNavigator
import tech.takahana.androidnavigationplayground.navigator.components.NavHostFragmentScreenNavigator
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationMessageDispatcher
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigationRequestDispatcher
import tech.takahana.androidnavigationplayground.navigator.components.ScreenNavigator
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ScreenNavigationDispatcherModule {

    @Singleton
    @Provides
    fun provideScreenNavigationRequestDispatcher(): ScreenNavigationRequestDispatcher {
        return ScreenNavigationRequestDispatcher()
    }

    @Singleton
    @Provides
    fun provideScreenNavigationMessageDispatcher(): ScreenNavigationMessageDispatcher {
        return ScreenNavigationMessageDispatcher()
    }
}

@InstallIn(ActivityComponent::class)
@Module
class ScreenNavigatorModule {
    @Provides
    fun provideScreenNavigator(
        screenNavigationMessageDispatcher: ScreenNavigationMessageDispatcher,
        screenNavigationRequestDispatcher: ScreenNavigationRequestDispatcher,
    ): ScreenNavigator {
        return DefaultScreenNavigator(
            screenNavigationMessageDispatcher,
            screenNavigationRequestDispatcher,
        )
    }

    @Provides
    fun provideNavHostFragmentScreenNavigatorFactory(
        screenNavigationMessageDispatcher: ScreenNavigationMessageDispatcher,
        screenNavigationRequestDispatcher: ScreenNavigationRequestDispatcher,
    ): NavHostFragmentScreenNavigator.Factory {
        return NavHostFragmentScreenNavigator.Factory(
            screenNavigationMessageDispatcher,
            screenNavigationRequestDispatcher,
        )
    }
}