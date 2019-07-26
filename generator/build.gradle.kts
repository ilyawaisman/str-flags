import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    id("maven-publish")
    id("com.jfrog.bintray") version "1.8.4"
}

dependencies {
    api("org.freemarker:freemarker:2.3.28")
}

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn(tasks.classes)
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val publishTaskName = "maven"
publishing {
    publications {
        register(publishTaskName, MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
            artifactId = "str-flags"
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_API_KEY")
    setPublications(publishTaskName)
    pkg (delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "str-flags"
        desc = "String representation for flags collections."
        userOrg = "ilyawaisman"
        setLicenses("MIT")
        vcsUrl = "https://github.com/ilyawaisman/str-flags.git"
        githubRepo = "ilyawaisman/str-flags"
    })
}
