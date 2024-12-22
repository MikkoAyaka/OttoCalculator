plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "priv.mikkoayaka.ottocalculator"
version = "1.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
application {
    mainClass.set("MainKt")
}
tasks.jar {
    manifest {
        attributes["Main-Class"] = "priv.mikkoayaka.ottocalculator.MainKt" // 确保 MANIFEST 文件中设置了主类
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE // 避免资源冲突
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}