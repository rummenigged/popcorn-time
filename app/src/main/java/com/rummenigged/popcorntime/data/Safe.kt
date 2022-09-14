package com.rummenigged.popcorntime.data

interface Safe<DomainType> {
    fun asDomain(): DomainType
}