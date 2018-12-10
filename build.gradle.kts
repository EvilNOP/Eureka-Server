import org.gradle.api.tasks.bundling.Jar
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    maven("http://maven.aliyun.com/nexus/content/groups/public/")
    maven("https://nexus.codemao.cn/nexus/content/repositories/releases/")
    mavenLocal()
    mavenCentral()
}

val ktlintVersion = "0.29.0"

// Tweak to be sure to have compiler and dependency versions the same
extra["kotlin.version"] = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion

plugins {
    val kotlinVersion = "1.3.10"
    val springBootVersion = "2.0.7.RELEASE"
    val dependencyManagementVersion = "1.0.6.RELEASE"

    application

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version dependencyManagementVersion
}

application {
    mainClassName = "io.github.EurekaKt"
}

group = "io.github"
version = "0.0.1-SNAPSHOT"

val ktlint by configurations.creating

val lint by task<JavaExec> {
    group = "ktlint"
    description = "Check Kotlin code style"
    classpath = configurations.getByName("ktlint")
    main = "com.github.shyiko.ktlint.Main"

    setArgs(listOf("src/**/*.kt"))
}

val format by task<JavaExec> {
    group = "ktlint"
    description = "Fix Kotlin code style deviations"
    classpath = configurations.getByName("ktlint")
    main = "com.github.shyiko.ktlint.Main"

    setArgs(listOf("-F", "src/**/*.kt"))
}

val installGitHook by task<JavaExec> {
    group = "ktlint"
    description = "Install git hook to automatically check files for style violations on commit"
    classpath = configurations.getByName("ktlint")
    main = "com.github.shyiko.ktlint.Main"

    setArgs(listOf("--install-git-pre-commit-hook"))
}

tasks.getByName("check") {
    setDependsOn(listOf(tasks.getByName("lint")))
}

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
    val springCloudVersion = "Finchley.SR2"

    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    ktlint("com.github.shyiko:ktlint:$ktlintVersion")

    compile("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
    compile("org.springframework:spring-webflux")
    compile("io.projectreactor.ipc:reactor-netty")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

inline fun <reified T : Task> task(noinline configuration: T.() -> Unit) = tasks.creating(T::class, configuration)
