package io.github.lee.plugins.depend.impl

import io.github.lee.plugins.depend.Versions
import io.github.lee.plugins.depend.handler.IHandlerDepend
import io.github.lee.plugins.depend.handler.api
import org.gradle.api.artifacts.dsl.DependencyHandler

object Adapter : IHandlerDepend {
    private val adapter = "io.github.cymchad:BaseRecyclerViewAdapterHelper4:${Versions.adapter}"
    override fun handler(handler: DependencyHandler) {
        handler.api(adapter)
    }

}