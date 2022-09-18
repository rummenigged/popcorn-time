package com.rummenigged.popcorntime.view.utils

import android.text.Html
import android.text.Spanned

fun String.fromHTML(): Spanned =
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
