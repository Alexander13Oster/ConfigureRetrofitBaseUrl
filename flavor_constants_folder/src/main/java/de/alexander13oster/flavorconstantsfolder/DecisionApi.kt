package de.alexander13oster.flavorconstantsfolder

import arrow.core.Either
import retrofit2.http.GET

interface DecisionApi {

    @GET("/api")
    suspend fun decide(): Either<Throwable, Decision>
}