buildscript {
    val kotlinVersion = "1.8.10"
    extra.set("kotlinVersion", kotlinVersion)

    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        classpath("com.google.gms:google-services:4.4.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }

    extra.apply {
        set("versionCode", 1)
        set("versionName", "1.0.0")
        set("applicationID", "com.onix.internship")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}