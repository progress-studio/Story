import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("module.publication")
    id("com.vanniktech.maven.publish") version "0.29.0"
    id("maven-publish")
    id("signing")
}

mavenPublishing {
    coordinates(
        groupId = "kr.progress.story",
        artifactId = "story",
        version = "0.0.1"
    )
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.None(),
            sourcesJar = true,
            androidVariantsToPublish = listOf("release")
        )
    )
    pom {
        name.set("Story")
        description.set("XML-based Story Format Processor")
        url.set("https://story.progress.kr")
        licenses {
            license {
                name.set("The MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        issueManagement {
            system.set("GitHub")
            url.set("https://github.com/progress-studio/Story/issues")
        }
        scm {
            connection.set("scm:git:git://github.com/progress-studio/Story.git")
            developerConnection.set("scm:git:ssh://git@github.com:progress-studio/Story.git")
            url.set("https://github.com/progress-studio/Story")
        }
        developers {
            developer {
                id.set("mercen")
                name.set("Mercen")
                email.set("mercen@progress.kr")
                organization.set("Progress")
                organizationUrl.set("https://progress.kr")
            }
        }
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

signing {
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PRIVATE_PASSWORD")
    )
    sign(publishing.publications)
}

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "kr.progress.story"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
