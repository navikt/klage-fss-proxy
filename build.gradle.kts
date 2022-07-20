import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springSleuthVersion = "3.0.4"
val tokenValidationVersion = "2.0.20"
val oidcSupportVersion = "0.2.18"
val logstashVersion = "7.0"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("org.springframework.boot") version "2.5.12"
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.10"
    idea
}

repositories {
    mavenCentral()
}

apply(plugin = "io.spring.dependency-management")

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("commons-pool:commons-pool")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth:$springSleuthVersion")

    implementation("no.nav.security:token-validation-spring:$tokenValidationVersion")
    implementation("no.nav.security:token-client-spring:$tokenValidationVersion")
    implementation("no.nav.security:oidc-spring-support:$oidcSupportVersion")

    implementation("io.micrometer:micrometer-registry-prometheus")
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
