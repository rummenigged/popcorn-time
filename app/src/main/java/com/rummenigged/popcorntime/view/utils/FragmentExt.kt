package com.rummenigged.popcorntime.view.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun Fragment.showLongToastMessage(message: String) =
    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()

fun Fragment.showShortToastMessage(message: String?) =
    try {
        Toast.makeText(activity, message ?: "Erro desconhecido", Toast.LENGTH_SHORT).show()
    } catch (e : Exception){

    }

fun <T>Fragment.observe(stateFlow: StateFlow<T>, collector: FlowCollector<T>){
    lifecycleScope.launch {
        stateFlow.collect(collector)
    }
}