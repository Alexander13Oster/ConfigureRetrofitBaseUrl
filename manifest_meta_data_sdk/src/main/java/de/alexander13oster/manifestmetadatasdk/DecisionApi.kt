package de.alexander13oster.manifestmetadatasdk

import arrow.core.Either
import retrofit2.http.GET

interface DecisionApi {

    @GET("/api")
    suspend fun decide(): Either<Throwable, Decision>
}