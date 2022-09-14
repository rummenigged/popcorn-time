package com.rummenigged.popcorntime.data

interface Raw<SafeType> {
    fun asSafe(): SafeType
}