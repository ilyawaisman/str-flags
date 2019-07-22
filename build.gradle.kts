tasks.wrapper {
    gradleVersion = "5.5.1"
    distributionType = Wrapper.DistributionType.ALL
}

plugins {
    java
}

dependencies {
    implementation("org.jetbrains:annotations:13.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.0")
}

repositories {
    jcenter()
}
