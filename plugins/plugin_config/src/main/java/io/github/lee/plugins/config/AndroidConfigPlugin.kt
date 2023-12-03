package io.github.lee.plugins.config

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.logger.quiet("Automatically configure Android project plugin loading")
    }
}