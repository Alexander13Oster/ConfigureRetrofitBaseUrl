package de.alexander13oster.manifestmetadata

import com.squareup.moshi.Json

data class Decision(
    @Json(name = "answer")
    val answer: String,
)