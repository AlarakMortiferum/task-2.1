import java.io.File

plugins {
    java
}

group = "ru.netology"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.29.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.9.3")
}

// Запускаем SUT в фоновом процессе через ProcessBuilder
val startSUT = tasks.register("startSUT") {
    doLast {
        println("Запуск SUT из artifacts/app-order.jar...")
        val pb = ProcessBuilder("java", "-jar", "artifacts/app-order.jar")
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT)
        pb.redirectError(ProcessBuilder.Redirect.INHERIT)
        pb.start()
        Thread.sleep(3000) // даём серверу время запуститься
    }
}

tasks.test {
    useJUnitPlatform()
    dependsOn(startSUT)
}

