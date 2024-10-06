package de.alexander13oster.manifestmetadatasdk

import dagger.Reusable
import javax.inject.Inject

@Reusable
class DecisionService @Inject constructor(
    private val decisionApi: DecisionApi,
) {
    suspend fun decide() = decisionApi.decide()
}