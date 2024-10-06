package de.alexander13oster.runtimeinterceptor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val decisionService: DecisionService,
    private val settingsRepository: SettingsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _breeds = MutableStateFlow(String())
    val breeds = _breeds.asStateFlow()

    val environment = settingsRepository
        .getEnvironment()
        .map { it == Environment.LOCAL }

    fun fetchBreeds() {
        viewModelScope.launch(ioDispatcher) {
            _breeds.update {
                decisionService.decide()
                    .fold({ "" }, { it.answer })
            }
        }
    }

    fun changeEnvironment(isLocal: Boolean) {
        settingsRepository.saveEnvironment(
            Environment.LOCAL
                .takeIf { isLocal }
                ?: Environment.PROD
        )
    }
}