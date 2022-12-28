import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val tokenValidationVersion = "3.0.2"
val oidcSupportVersion = "0.2.18"
val logstashVersion = "7.2"

plugins {
    val kotlinVersion = "1.7.22"
    id("org.springframework.boot") version "3.0.1"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    idea
}

repositories {
    mavenCentral()
}

apply(plugin = "io.spring.dependency-management")

dependencies {
    implementation(kotlin("stdlib"))

    runtimeOnly("org.springframework.boot:spring-boot-properties-migrator")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("commons-pool:commons-pool")

    implementation("no.nav.security:token-validation-spring:$tokenValidationVersion")
    implementation("no.nav.security:token-client-spring:$tokenValidationVersion")
    implementation("no.nav.security:oidc-spring-support:$oidcSupportVersion")

    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")

    implementation("ch.qos.logback:logback-classic")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
}

idea {
    module {
        isDownloadJavadoc = true
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions{
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.archiveFileName.set("app.jar")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
