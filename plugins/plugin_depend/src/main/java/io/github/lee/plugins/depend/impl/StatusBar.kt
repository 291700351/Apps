package io.github.lee.plugins.depend.impl

import io.github.lee.plugins.depend.Versions
import io.github.lee.plugins.depend.handler.IHandlerDepend
import io.github.lee.plugins.depend.handler.api
import org.gradle.api.artifacts.dsl.DependencyHandler

object StatusBar : IHandlerDepend {
    private const val immersionbar =
        "com.geyifeng.immersionbar:immersionbar:${Versions.immersionbar}"
    private const val immersionbarKtx =
        "com.geyifeng.immersionbar:immersionbar-ktx:${Versions.immersionbar}"


    override fun handler(handler: DependencyHandler) {
        handler.api(immersionbar)
        handler.api(immersionbarKtx)
    }


}