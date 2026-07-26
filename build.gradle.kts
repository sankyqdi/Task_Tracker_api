plugins {
    java
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://spring.io") }
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation ("org.mapstruct:mapstruct:1.6.3")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    runtimeOnly ("org.postgresql:postgresql")

    runtimeOnly("com.h2database:h2")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.6.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

