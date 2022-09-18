package com.rummenigged.popcorntime.utils

import java.io.File
import java.io.FileNotFoundException
import java.net.URL

fun getRawResource(fileName: String?): String =
    ClassLoader.getSystemResource("raw/$fileName")?.readText() ?:
        throw FileNotFoundException("The raw file $fileName was not found")
