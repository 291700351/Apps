import io.github.lee.plugins.config.impl.setupLibrary
import io.github.lee.plugins.depend.impl.LibBase

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val namespace = "io.github.lee.room"
val resPrefix = null
setupLibrary(namespace, resPrefix) {

}


dependencies {
    LibBase.handler(this)
}