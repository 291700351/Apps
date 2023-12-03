package io.github.lee.core.vm.data

import io.github.lee.core.vm.err.ViewModelException

sealed interface UiState {
    data object None : UiState
    data class Loading(val tip: String? = null) : UiState
    data object Success: UiState
    data class Empty(val t: ViewModelException? = null): UiState
    data class Error(val t: ViewModelException? = null): UiState
}