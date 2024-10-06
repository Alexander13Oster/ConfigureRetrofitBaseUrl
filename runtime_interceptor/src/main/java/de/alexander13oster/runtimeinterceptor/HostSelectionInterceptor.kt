package de.alexander13oster.runtimeinterceptor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HostSelectionInterceptor @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : Interceptor {

    private val coroutineScope = CoroutineScope(ioDispatcher)

    private var environment: Environment? = null

    init {
        coroutineScope.launch(ioDispatcher) {
            settingsRepository
                .getEnvironment()
                .collectLatest { environment = it }
        }
    }

    override fun intercept(chain: Interceptor.Chain) = chain
        .request()
        .let { request ->
            environment
                ?.let { environment ->
                    request.url
                        .newBuilder()
                        .host(environment.host)
                        .port(environment.port)
                        .build()
                }
                ?.let { url ->
                    request.newBuilder()
                        .url(url)
                        .build()
                } ?: request
        }.let(chain::proceed)
}