package com.rummenigged.popcorntime.data.common

interface Safe<DomainType> {
    fun asDomain(): DomainType
}