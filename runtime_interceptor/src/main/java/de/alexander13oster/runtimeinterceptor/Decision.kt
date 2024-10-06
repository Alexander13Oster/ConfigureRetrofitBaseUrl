package de.alexander13oster.runtimeinterceptor

import com.squareup.moshi.Json

data class Decision(
    @Json(name = "answer")
    val answer: String,
)