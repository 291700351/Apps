package io.github.lee.plugins.depend.impl

import io.github.lee.plugins.depend.Versions
import io.github.lee.plugins.depend.handler.IHandlerDepend
import io.github.lee.plugins.depend.handler.api
import org.gradle.api.artifacts.dsl.DependencyHandler

object DataStore : IHandlerDepend {
    private val preferences = "androidx.datastore:datastore-preferences:${Versions.dataStore}"
    private val proto = "androidx.datastore:datastore:${Versions.dataStore}"

    override fun handler(handler: DependencyHandler) {
        handler.api(preferences)
        handler.api(proto)
    }

}