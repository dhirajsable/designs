import java.util.Date
import java.text.SimpleDateFormat

plugins {
    kotlin("jvm") version "1.9.21"
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.spotless)
    alias(libs.plugins.springManagement)
    alias(libs.plugins.springBoot) apply(false)
    alias(libs.plugins.openapi) apply(false)
    alias(libs.plugins.swaggerGenerator) apply(false)
    alias(libs.plugins.docker) apply(false)
    alias(libs.plugins.freefairLombok)
}

allprojects {
    apply {
        plugin("java-library")
        plugin("groovy")
    }
    version = SimpleDateFormat("yyyymmdd.hhmm").format(Date())

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
        withSourcesJar()
    }

    dependencies {
        implementation(platform(rootProject.libs.springBootBom))
        implementation(platform(rootProject.libs.springCloudBom))
        implementation(platform(rootProject.libs.springBootSecurityBom))
        implementation(platform(rootProject.libs.kotlinBom))
        implementation(platform(rootProject.libs.resilience4jBom))
        implementation(rootProject.libs.slf4jApi)
        implementation(rootProject.libs.googlejavaformat)
        implementation(rootProject.libs.bundles.testFrameworkCompiler)
        implementation(rootProject.libs.bundles.testFrameworkRuntime)
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("com.diffplug.spotless")
        plugin("maven-publish")
    }
    spotless {
        configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            configure<com.diffplug.gradle.spotless.SpotlessExtension> {
                kotlin {
                    target("**/*.kt")
                    targetExclude("$buildDir/**/*.kt")

                    ktlint()
                    licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
                }

                kotlinGradle {
                    target("*.gradle.kts")
                    ktlint()
                }
                java {
                    importOrder()
                    cleanthat()
                    googleJavaFormat("1.23.0").aosp().reflowLongStrings().formatJavadoc(false).reorderImports(false).groupArtifact("com.google.googlejavaformat:google-java-format")
                    formatAnnotations()
                }
                groovy {
                    importOrder()
                    removeSemicolons()
                    greclipse()
                    excludeJava()
                }
            }
        }
    }
    //Upload/publish artifiacts
    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
                from(components["java"])
            }
        }

        repositories {
            mavenLocal()
        }
    }
}

lombok {
    version.set(rootProject.libs.versions.lombok)
}