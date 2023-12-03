// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("com.android.library") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false

    val pluginDir = File("./plugins")
    pluginDir.listFiles()?.forEach {
        if (it.name.startsWith("_")) {
            return@forEach
        }
        if (!it.exists() || !it.isDirectory) {
            return@forEach
        }
        val gradleFile = File(it, "build.gradle")
        val gradleKtsFile = File(it, "build.gradle.kts")
        if ((gradleFile.exists() && gradleFile.isFile)
            || (gradleKtsFile.exists() && gradleKtsFile.isFile)
        ) {
            //加载插件
            id("io.github.lee.plugin.${it.name}") apply true
        }
    }

}
