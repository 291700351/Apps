plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

gradlePlugin {
    plugins {
        create(project.name) {
            id = "io.github.lee.plugin.${project.name}"
            this.displayName = project.name
            this.description = "依賴統一管理"
            this.implementationClass = "io.github.lee.plugins.depend.AndroidDependPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.1.4")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
}
