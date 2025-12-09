package br.com.vandodev.copa_2022_android.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.vandodev.copa_2022_android.MainViewModel
import br.com.vandodev.domain.usecase.DisableNotificationUseCase
import br.com.vandodev.domain.usecase.EnableNotificationUseCase
import br.com.vandodev.domain.usecase.GetMatchesUseCase

class ViewModelFactory(private val appContainer: AppContainer) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                GetMatchesUseCase(appContainer.matchesRepository),
                EnableNotificationUseCase(appContainer.notificationRepository),
                DisableNotificationUseCase(appContainer.notificationRepository)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}