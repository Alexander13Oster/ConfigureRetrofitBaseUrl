package de.alexander13oster.flavorconstantsfolder

import com.squareup.moshi.Json

data class Decision(
    @Json(name = "answer")
    val answer: String,
)