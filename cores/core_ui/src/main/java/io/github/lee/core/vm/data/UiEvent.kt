package io.github.lee.core.vm.data

import io.github.lee.core.vm.err.ViewModelException

sealed interface UiEvent {

    data object None : UiEvent
    data class ShowProgress(val tip: String? = null, val canDismiss: Boolean? = true) : UiEvent
    data class HideProgress(val runnable: Runnable? = null) : UiEvent
    data class Toast(val msg: String? = null, val isLong: Boolean = false) : UiEvent

    data object RefreshSuccess : UiEvent
    data class RefreshFail(val e: ViewModelException?) : UiEvent
    data class LoadMoreSuccess(val hasMore: Boolean? = true) : UiEvent
    data class LoadMoreFail(val e: ViewModelException?) : UiEvent


}