package io.github.lee.plugins.depend.impl

import io.github.lee.plugins.depend.Versions
import io.github.lee.plugins.depend.handler.IHandlerDepend
import io.github.lee.plugins.depend.handler.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

object AppBase : IHandlerDepend {
    private const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"

    override fun handler(handler: DependencyHandler) {
        LibBase.handler(handler)
        handler.implementation(constraintlayout)
    }

}