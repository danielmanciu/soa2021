package com.soa.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.soa.VoteApplication
import com.soa.data.remote.LoginApi
import com.soa.data.remote.VoteApi
import com.soa.data.repository.VoteRepository
import com.soa.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import timber.log.Timber

@Module
class AppModule {

    @Provides
    @ApplicationScope
    internal fun provideApplication(application: Application): VoteApplication =
        application as VoteApplication

    @Provides
    @ApplicationScope
    internal fun provideContext(application: Application): Context = application

    @Provides
    @ApplicationScope
    internal fun provideTimberTree(): Timber.Tree = Timber.DebugTree()

    @Provides
    @ApplicationScope
    internal fun provideViewModelFactory(
        preferences: SharedPreferences,
        repository: VoteRepository
    ) = ViewModelFactory(preferences, repository)

    @Provides
    @ApplicationScope
    internal fun provideVoteRepository(loginApi: LoginApi, voteApi: VoteApi) =
        VoteRepository(loginApi, voteApi)

    @Provides
    @ApplicationScope
    internal fun provideSharedPreferences(context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)
}