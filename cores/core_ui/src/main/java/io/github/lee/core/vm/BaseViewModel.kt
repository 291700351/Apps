package io.github.lee.core.vm

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import io.github.lee.core.vm.data.UiEvent
import io.github.lee.core.vm.data.UiState
import io.github.lee.core.vm.err.ViewModelException
import io.github.lee.core.vm.lifecycle.ViewModelLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application),
    ViewModelLifecycle {
    private val _uiState = MutableSharedFlow<UiState>()
    private val _uiEvent = MutableSharedFlow<UiEvent>()


    //===========
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

    }

    // =====
    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context, start, block)

    protected fun <T> launchResult(
        block: suspend CoroutineScope.() -> T,
        error: (suspend (t: Throwable?) -> Unit)? = null,
        success: suspend CoroutineScope.(data: T) -> Unit
    ) {
        try {
            val handle = CoroutineExceptionHandler { _, throwable ->
                launch(Dispatchers.Main) {
                    error?.invoke(throwable)
                }
            }
            launch(handle + Dispatchers.IO) {
                val result = withContext(Dispatchers.IO) {
                    block()
                }
                if (null == result) {
                    launch(Dispatchers.Main) {
                        error?.invoke(null)
                    }
                } else {
                    launch(Dispatchers.Main) {
                        success(result)
                    }
                }
            }

        } catch (e: Exception) {
            launch(Dispatchers.Main) {
                error?.invoke(e)
            }
        }
    }

    //===============
    protected fun emitUiState(state: UiState) {
        launch(Dispatchers.IO) {
            _uiState.emit(state)
        }
    }

    fun collectUiState(collector: FlowCollector<UiState>) {
        launch(Dispatchers.Main) {
            _uiState.collect(collector)
        }
    }


      fun loading(tip: String? = null) =
        emitUiState(UiState.Loading(tip))

    fun success() =
        emitUiState(UiState.Success)

    fun empty(e: ViewModelException? = null) =
        emitUiState(UiState.Empty(e))

    fun error(e: ViewModelException? = null) =
        emitUiState(UiState.Error(e))

    //====
    private fun emitUiEvent(event: UiEvent) {
        launch(Dispatchers.IO) {
            _uiEvent.emit(event)
        }
    }

    fun collectUiEvent(collector: FlowCollector<UiEvent>) {
        launch(Dispatchers.Main) {
            _uiEvent.collect(collector)
        }
    }


    fun showProgress(tip: String? = null, canDismiss: Boolean? = true) {
        emitUiEvent(UiEvent.ShowProgress(tip, canDismiss))
    }

    fun hideProgress(runnable: Runnable? = null) {
        emitUiEvent(UiEvent.HideProgress(runnable))
    }

    fun toast(msg: String? = null, isLong: Boolean = false) {
        emitUiEvent(UiEvent.Toast(msg, isLong))
    }

    fun refreshSuccess() {
        emitUiEvent(UiEvent.RefreshSuccess)
    }

    fun refreshFail(e: ViewModelException? = null) {
        emitUiEvent(UiEvent.RefreshFail(e))
    }

    fun loadMoreSuccess(hasMore: Boolean = true) {
        emitUiEvent(UiEvent.LoadMoreSuccess(hasMore))
    }

    fun loadMoreFail(e: ViewModelException? = null) {
        emitUiEvent(UiEvent.LoadMoreFail(e))
    }

    //===Desc:======================================================================================
    protected fun getString(@StringRes resId: Int): String =
        getApplication<Application>().getString(resId)


    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
        getApplication<Application>().getString(resId, formatArgs)


}