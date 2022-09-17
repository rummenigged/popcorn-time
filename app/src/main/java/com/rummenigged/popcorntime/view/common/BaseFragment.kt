package com.rummenigged.popcorntime.view.common

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    @LayoutRes private val layoutId: Int
): Fragment(layoutId) {

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView(savedInstanceState)
        setupObservers()
    }

    protected open fun setupView(savedInstanceState: Bundle?){}

    protected open fun setupObservers(){}
}