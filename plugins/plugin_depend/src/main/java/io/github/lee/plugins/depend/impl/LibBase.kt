package io.github.lee.plugins.depend.impl

import io.github.lee.plugins.depend.Versions
import io.github.lee.plugins.depend.handler.IHandlerDepend
import io.github.lee.plugins.depend.handler.androidTestImplementation
import io.github.lee.plugins.depend.handler.implementation
import io.github.lee.plugins.depend.handler.testImplementation
import org.gradle.api.artifacts.dsl.DependencyHandler

object LibBase : IHandlerDepend {
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private const val material = "com.google.android.material:material:${Versions.material}"

    private const val junit = "junit:junit:${Versions.junit}"
    private const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunit}"
    private const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    override fun handler(handler: DependencyHandler) {
        handler.implementation(coreKtx)
        handler.implementation(appcompat)
        handler.implementation(material)
        handler.testImplementation(junit)
        handler.androidTestImplementation(androidxJunit)
        handler.androidTestImplementation(espresso)
    }


}