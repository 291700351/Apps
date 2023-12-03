pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Apps"

fun loadSubprojects(dir: File, block: (File) -> Unit) {
    if (!dir.exists() || !dir.isDirectory) {
        val success = dir.mkdirs()
        if (!success) {
            val error = "Failed to create folder in '${coresDir.absolutePath}'"
            throw GradleScriptException(error, Exception(error))
        }
    }

    dir.listFiles()?.forEach {
        if (!it.exists() || !it.isDirectory) {
            return
        }
        if (!it.name.startsWith("_")) {
            val gradleFile = File(it, "build.gradle")
            val gradleKtsFile = File(it, "build.gradle.kts")
            if ((gradleFile.exists() && gradleFile.isFile)
                || (gradleKtsFile.exists() && gradleKtsFile.isFile)
            ) {
                block(it)
            }
        }
    }
}
// Load local plugins
val pluginsDir = File(rootDir, "plugins")
loadSubprojects(pluginsDir) {
    logger.quiet("> Plugin :${it.name} auto include for build")
    includeBuild(it.absolutePath)
}

// Load local cores
val coresDir = File(rootDir, "cores")
loadSubprojects(coresDir) {
    val projectName = it.name
    logger.quiet("> Auto include core library '$projectName'")
    include(":$projectName")
    project(":$projectName").projectDir = it.absoluteFile
}

// Load local libs
val libsDir = File(rootDir, "libs")
loadSubprojects(libsDir) {
    val projectName = it.name
    logger.quiet("> Auto include library '$projectName'")
    include(":$projectName")
    project(":$projectName").projectDir = it.absoluteFile
}

// Load local apps
val appsDir = File(rootDir, "apps")
loadSubprojects(appsDir) {
    val projectName = it.name
    logger.quiet("> Auto include app '$projectName'")
    include(":$projectName")
    project(":$projectName").projectDir = it.absoluteFile
}
