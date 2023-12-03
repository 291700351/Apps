package io.github.lee.plugins.config.data

import java.io.File

data class ApkSignature(
    val name: String,
    val storeFile: File,
    val storePassword: String,
    val keyAlias: String,
    val keyPassword: String
)

