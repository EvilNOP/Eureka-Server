import org.gradle.api.tasks.bundling.Jar
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    maven("http://maven.aliyun.com/nexus/content/groups/public/")
    maven("https://nexus.codemao.cn/nexus/content/repositories/releases/")
    mavenLocal()
    mavenCentral()
}

// Tweak to be sure to have compiler and dependency versions the same
extra["kotlin.version"] = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion

plugins {
    val kotlinterVersion = "1.16.0"
    val kotlinVersion = "1.2.61"
    val springBootVersion = "2.0.5.RELEASE"
    val dependencyManagementVersion = "1.0.6.RELEASE"

    application

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version dependencyManagementVersion

    id("org.jmailen.kotlinter") version kotlinterVersion
}

application {
    mainClassName = "io.github.EurekaKt"
}

group = "io.github"
version = "0.0.1-SNAPSHOT"

tasks.withType<Jar> {
    baseName = "eureka"
    version = "gradle"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencyManagement {
    val springCloudVersion = "Finchley.SR1"

    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    compile("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
    compile("org.springframework:spring-webflux")
    compile("io.projectreactor.ipc:reactor-netty")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
