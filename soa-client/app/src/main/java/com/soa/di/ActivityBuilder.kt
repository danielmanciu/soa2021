package com.soa.di

import com.soa.di.scope.ActivityScope
import com.soa.page.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentProvider::class])
    abstract fun provideMainActivity(): MainActivity
}