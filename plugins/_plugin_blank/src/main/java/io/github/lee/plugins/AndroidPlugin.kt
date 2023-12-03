package io.github.lee.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

class AndroidDependPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.logger.quiet(
            "Please implement your own plugin"
        )
    }
}
