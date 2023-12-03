import io.github.lee.plugins.config.impl.setupLibrary
import io.github.lee.plugins.depend.impl.Adapter
import io.github.lee.plugins.depend.impl.LibBase
import io.github.lee.plugins.depend.impl.Lifecycle

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

val namespace = "io.github.lee.ui"
val resPrefix = "ui_"

setupLibrary(namespace, resPrefix) {
    buildFeatures {
        dataBinding = true
    }
}


dependencies {
    LibBase.handler(this)
    Lifecycle.handler(this)
    Adapter.handler(this)
}