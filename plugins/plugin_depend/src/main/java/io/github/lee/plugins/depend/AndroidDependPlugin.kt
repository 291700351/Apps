package io.github.lee.plugins.depend

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

class AndroidDependPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.logger.log(
            LogLevel.QUIET,
            "Loading projects relies on the Unified Management plugin"
        )
    }
}
