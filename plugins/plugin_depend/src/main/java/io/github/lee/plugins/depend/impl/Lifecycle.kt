package io.github.lee.plugins.depend.impl

import io.github.lee.plugins.depend.Versions
import io.github.lee.plugins.depend.handler.IHandlerDepend
import io.github.lee.plugins.depend.handler.implementation
import io.github.lee.plugins.depend.handler.kapt
import org.gradle.api.artifacts.dsl.DependencyHandler

object Lifecycle : IHandlerDepend {
    private const val lifecycleViewmodelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    // LiveData
    private const val lifecycleLivedataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"

    // Saved state module for ViewModel
    private const val lifecycleViewmodelSavedstate =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"

    // Annotation processor
    private const val lifecycleCompiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"

    override fun handler(handler: DependencyHandler) {
        handler.implementation(lifecycleViewmodelKtx)
        handler.implementation(lifecycleLivedataKtx)
        handler.implementation(lifecycleViewmodelSavedstate)
        handler.kapt(lifecycleCompiler)
    }

}