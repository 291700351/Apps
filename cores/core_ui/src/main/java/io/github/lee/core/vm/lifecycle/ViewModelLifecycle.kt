package io.github.lee.core.vm.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleEventObserver

interface ViewModelLifecycle : DefaultLifecycleObserver, LifecycleEventObserver {

}
