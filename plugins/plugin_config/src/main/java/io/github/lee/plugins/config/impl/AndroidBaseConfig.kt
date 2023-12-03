package io.github.lee.plugins.config.impl

import com.android.build.gradle.BaseExtension
import io.github.lee.plugins.config.constant.BuildConfig
import org.gradle.api.GradleScriptException
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun BaseExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}


inline fun <reified T : BaseExtension> Project.setupAndroidBase(
    namespace: String, crossinline block: T.() -> Unit = {}
) {
    try {
        extensions.configure<T>("android") {
            this.namespace = namespace
            //compileSdk = 34
            this.compileSdkVersion(BuildConfig.compileSdk)
            defaultConfig {
                minSdk = BuildConfig.minSdk
                targetSdk = BuildConfig.targetSdk
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            kotlinOptions {
                jvmTarget = "17"
                allWarningsAsErrors = true
                val arguments = mutableListOf(
                    "-progressive",
                    "-Xjvm-default=all",
                    "-Xno-call-assertions",
                    "-Xno-param-assertions",
                    "-Xno-receiver-assertions",
                )
                freeCompilerArgs = freeCompilerArgs + arguments
            }

            block()
        }

    } catch (e: Exception) {
        throw GradleScriptException("Config android project fail", e)
    }
}

