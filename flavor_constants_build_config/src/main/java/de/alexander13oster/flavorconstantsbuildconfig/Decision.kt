package de.alexander13oster.flavorconstantsbuildconfig

import com.squareup.moshi.Json

data class Decision(
    @Json(name = "answer")
    val answer: String,
)