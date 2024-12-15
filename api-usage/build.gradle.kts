plugins {
    alias(libs.plugins.springBoot)
    alias(libs.plugins.docker)
    alias(libs.plugins.swaggerGenerator)
}

dependencies {
    api(libs.springBootStarterActuator)
    api(libs.springBootStarterWeb)
    api(libs.springBootStarterDatJpa)
    implementation(libs.springBootStarterValidator)
    api(libs.postgresql)
    api(libs.commonsLogging)
    api(libs.groovy)
    api(libs.groovyAll)
    api(libs.micrometer)
    api(libs.jakartaValidationApi)
    api(libs.swaggerAnnotations)
    api(libs.springdocOpenapiStarter)
    api("jakarta.persistence:jakarta.persistence-api:2.2.3")
    api("jakarta.servlet:jakarta.servlet-api:6.1.0")
    api("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("org.springframework.kafka:spring-kafka:3.3.0")
}

description = "api-usage"

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.apache.groovy:groovy")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
