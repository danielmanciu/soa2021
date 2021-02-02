package com.soa.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soa.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<VM : ViewModel>(private val kClass: KClass<VM>) : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = ViewModelProvider(this, viewModelFactory).get(kClass.java)
    }
}
