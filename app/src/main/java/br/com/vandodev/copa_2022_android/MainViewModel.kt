package br.com.vandodev.copa_2022_android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vandodev.domain.model.Match
import br.com.vandodev.domain.usecase.DisableNotificationUseCase
import br.com.vandodev.domain.usecase.EnableNotificationUseCase
import br.com.vandodev.domain.usecase.GetMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class MainUiState(
    val matches: List<Match> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MainViewModel(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val enableNotificationUseCase: EnableNotificationUseCase,
    private val disableNotificationUseCase: DisableNotificationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        getMatchesUseCase()
            .onEach { matches ->
                _uiState.value = MainUiState(matches = matches)
            }
            .catch { error ->
                _uiState.value = MainUiState(errorMessage = "Could not load match data. Check your internet connection.")
            }
            .launchIn(viewModelScope)
    }

    fun toggleNotification(match: Match) {
        viewModelScope.launch {
            if (match.notificationEnabled) {
                disableNotificationUseCase(match.id)
            } else {
                enableNotificationUseCase(match.id)
            }
        }
    }
}