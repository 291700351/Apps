import io.github.lee.plugins.config.data.ApkSignature
import io.github.lee.plugins.config.impl.setupApplication
import io.github.lee.plugins.depend.impl.AppBase

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
val applicationId = "io.github.lee.demo"
val vc = 1
val vn = "1.0.0"
val apkSigning = ApkSignature(
    "demo", rootProject.file("signs${File.separator}demo.jks"),
    "123456", "demo", "123456"
)

setupApplication(applicationId, vc, vn, apkSigning) {

}



dependencies {
    AppBase.handler(this)
}