package io.github.lee.plugins.depend.handler

import org.gradle.api.artifacts.dsl.DependencyHandler

interface IHandlerDepend {
    fun handler(handler: DependencyHandler)
}