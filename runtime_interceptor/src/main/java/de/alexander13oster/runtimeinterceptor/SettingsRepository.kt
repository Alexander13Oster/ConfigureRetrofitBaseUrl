package de.alexander13oster.runtimeinterceptor

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.Reusable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@Reusable
class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    private val scope = CoroutineScope(ioDispatcher)
    private val environmentKey = stringPreferencesKey("environment")

    fun getEnvironment(): Flow<Environment> = dataStore
        .data
        .map { preferences ->
            preferences[environmentKey]
                ?.let { Environment.valueOf(it) }
                ?: Environment.PROD
        }

    fun saveEnvironment(environment: Environment) {
        scope.launch {
            dataStore.edit {
                it[environmentKey] = environment.name
            }
        }
    }
}