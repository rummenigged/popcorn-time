package com.rummenigged.popcorntime.view.common

abstract class DiffUtilItemList<T>{
    abstract fun isItemEqualTo(other: T): Boolean

    abstract fun isContentEqualTo(other: T): Boolean
}