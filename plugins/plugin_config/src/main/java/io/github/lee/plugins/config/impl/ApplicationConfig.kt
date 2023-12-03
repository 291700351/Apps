package io.github.lee.plugins.config.impl

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import io.github.lee.plugins.config.data.ApkSignature
import org.gradle.api.Project

fun Project.setupApplication(
    applicationId: String,
    versionCode: Int, versionName: String,
    apkSigning: ApkSignature? = null,
    block: BaseAppModuleExtension.() -> Unit = {}
) {
    setupAndroidBase<BaseAppModuleExtension>(applicationId) {
        defaultConfig {
            this.applicationId = applicationId
            this.versionCode = versionCode
            this.versionName = versionName
        }
        //签名
        if (null != apkSigning) {
            signingConfigs {
                create(apkSigning.name) {
                    storeFile = apkSigning.storeFile
                    storePassword = apkSigning.storePassword
                    keyAlias = apkSigning.keyAlias
                    keyPassword = apkSigning.keyPassword
                    enableV1Signing = true
                    enableV2Signing = true
                    enableV3Signing = true
                    enableV4Signing = true
                }
            }
        }

        buildTypes {
            debug {
                if (null != apkSigning) {
                    signingConfig = signingConfigs.getByName(apkSigning.name)
                }
                isMinifyEnabled = true
                isDebuggable = true
                isJniDebuggable = true
                isRenderscriptDebuggable = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
                )
            }
            release {
                if (null != apkSigning) {
                    signingConfig = signingConfigs.getByName(apkSigning.name)
                }
                isMinifyEnabled = true
                isDebuggable = false
                isJniDebuggable = false
                isRenderscriptDebuggable = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
                )
            }
        }
        block()
    }

}
