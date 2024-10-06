package de.alexander13oster.manifestmetadata

import arrow.core.Either
import retrofit2.http.GET

interface DecisionApi {

    @GET("/api")
    suspend fun decide(): Either<Throwable, Decision>
}