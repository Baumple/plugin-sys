plugins {
    id("java")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(files("./notification-plugin/lib/build/libs/lib.jar"))

    // Toml parser
    implementation("com.moandjiezana.toml:toml4j:0.7.2")
}

application {
    mainClass = "org.example.Main"
}

tasks.test {
    useJUnitPlatform()
}
