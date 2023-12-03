package io.github.lee.plugins.config.impl

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project


fun Project.setupLibrary(
    namespace: String,
    resPrefix: String? = null,
    block: LibraryExtension.() -> Unit = {}
) {
    setupAndroidBase<LibraryExtension>(namespace) {
        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
            if (!resPrefix.isNullOrEmpty()) {
                resourcePrefix = resPrefix
            }
        }
        buildTypes {
            debug {
                isMinifyEnabled = true
                isJniDebuggable = true
                isRenderscriptDebuggable = true
                consumerProguardFiles("proguard-rules.pro")
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            release {
                isMinifyEnabled = true
                isJniDebuggable = false
                isRenderscriptDebuggable = false
                consumerProguardFiles("proguard-rules.pro")
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        block()
    }

}
