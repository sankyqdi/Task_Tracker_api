import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "4.1.0"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

tasks.named<BootJar>("bootJar") {
    archiveFileName.set("app.jar")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

val lombokVersion = "1.18.34"

dependencies {

    implementation(platform("org.springframework.boot:spring-boot-dependencies:4.1.0"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.3.4")

    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

tasks.withType<Test> {
    useJUnitPlatform()
}

