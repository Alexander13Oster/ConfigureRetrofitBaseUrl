package de.alexander13oster.manifestmetadatasdk

import com.squareup.moshi.Json

data class Decision(
    @Json(name = "answer")
    val answer: String,
)