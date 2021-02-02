package com.soa.di

import com.soa.di.scope.FragmentScope
import com.soa.page.vote.VoteFragment
import com.soa.page.login.LoginFragment
import com.soa.page.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideLoginFragment(): LoginFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideVoteFragment(): VoteFragment

//    @FragmentScope
//    @ContributesAndroidInjector
//    abstract fun provideRegistrationFragment(): RegistrationFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideSplashFragment(): SplashFragment
}