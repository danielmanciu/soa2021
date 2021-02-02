package com.soa.di

import android.app.Application
import com.soa.VoteApplication
import com.soa.data.remote.ApiModule
import com.soa.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        ApiModule::class,
        AppModule::class
    ]
)
@ApplicationScope
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: VoteApplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}