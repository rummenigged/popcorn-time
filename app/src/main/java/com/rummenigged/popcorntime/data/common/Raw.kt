package com.rummenigged.popcorntime.data.common

interface Raw<SafeType> {
    fun asSafe(): SafeType
}