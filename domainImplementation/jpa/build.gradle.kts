plugins {
    id("java")
}

group = "cat.uvic.teknos"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation("org.hibernate:hibernate-core:6.5.0.Final")
    implementation("com.mysql:mysql-connector-j:8.3.0")

}

tasks.test {
    useJUnitPlatform()
}