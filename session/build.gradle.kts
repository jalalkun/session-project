plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka") version "1.7.20"
    id("com.gladed.androidgitversion") version "0.4.14"
    id("se.premex.ownership") version "0.0.11"
}

ownership {
    validateOwnership = true
    generateGithubOwners = true
    generateMissingOwnershipFiles = true
    defaultOwnerForMissingOwnershipFiles = "@jalalkun"
}

androidGitVersion {
    tagPattern = "^v[0-9]+.*"
}


val PUBLISH_GROUP_ID: String by extra("com.jalalkun.session")
val PUBLISH_VERSION: String by extra(androidGitVersion.name().replace("v", ""))
val PUBLISH_ARTIFACT_ID by extra("core")

apply(from = "${rootProject.projectDir}/gradle/publish-module.gradle")
android {

    compileSdk = 33
    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        targetSdk = 33
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = false
        compose = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOfNotNull(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xskip-prerelease-check"
        )
    }

    lint {
        baseline = file("lint-baseline.xml")
        checkReleaseBuilds = true
        checkAllWarnings = true
        warningsAsErrors = true
        abortOnError = true
        disable.add("LintBaseline")
        disable.add("GradleDependency")
        checkDependencies = true
        checkGeneratedSources = false
        sarifOutput = file("../lint-results-lib.sarif")
    }
    buildToolsVersion = "33.0.1"
    namespace = "com.jalalkun.session"
}

kotlin {
    // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors
    explicitApi()
}


dependencies {
    implementation("com.google.code.gson:gson:2.10")
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha04")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
}