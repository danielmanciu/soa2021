package com.soa

import com.soa.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class VoteApplication : DaggerApplication() {

    @Inject
    lateinit var timber: Timber.Tree

    override fun onCreate() {
        super.onCreate()
        Timber.plant(timber)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build().also {
                it.inject(this)
            }
}