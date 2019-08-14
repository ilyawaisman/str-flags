tasks.wrapper {
    gradleVersion = "5.5.1"
    distributionType = Wrapper.DistributionType.ALL
}

repositories {
    jcenter()
}

plugins {
    idea
    `java-library`
}

val javaVersion = JavaVersion.VERSION_1_8

idea {
    project.jdkName = javaVersion.name
}

subprojects {
    group = "xyz.prpht.setflags"
    version = "0.1.2"

    repositories {
        jcenter()
    }

    apply<JavaLibraryPlugin>()

    configure<JavaPluginConvention> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    val implementation by configurations
    val testImplementation by configurations

    dependencies {
        implementation("org.jetbrains:annotations:13.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.0")
    }
}
