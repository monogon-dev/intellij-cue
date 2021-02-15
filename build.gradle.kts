import org.jetbrains.changelog.closure
import org.jetbrains.changelog.markdownToHTML

plugins { // Java support
    id("java") // gradle-intellij-plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
    id("org.jetbrains.intellij") version "0.6.5" // gradle-changelog-plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
    id("org.jetbrains.changelog") version "1.1.2"
    id("org.jetbrains.grammarkit") version "2020.3.2"
}

// Import variables from gradle.properties file
val pluginGroup: String by project // `pluginName_` variable ends with `_` because of the collision with Kotlin magic getter in the `intellij` closure. // Read more about the issue: https://github.com/JetBrains/intellij-platform-plugin-template/issues/29
val pluginName_: String by project
val pluginVersion: String by project
val pluginSinceBuild: String by project
val pluginUntilBuild: String by project
val pluginVerifierIdeVersions: String by project

val platformType: String by project
val platformVersion: String by project
val platformPlugins: String by project
val platformDownloadSources: String by project

group = pluginGroup
version = pluginVersion

// Configure project's dependencies
repositories {
    mavenCentral()
    jcenter()
}

// setup additional source folders
sourceSets.main {
    java.srcDir("src/main/java-gen")
}

// Configure gradle-intellij-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName = pluginName_
    version = platformVersion
    type = platformType
    downloadSources = platformDownloadSources.toBoolean()
    updateSinceUntilBuild = true

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    setPlugins(*platformPlugins.split(',').map(String::trim).filter(String::isNotEmpty).toTypedArray())
}

// Configure gradle-changelog-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version = pluginVersion
}

tasks { // disable building searchable options to speed up build, we currently don't settings UI
    buildSearchableOptions {
        enabled = false
    }

    // Set the compatibility versions to 1.8
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        version(pluginVersion)
        sinceBuild(pluginSinceBuild)
        untilBuild(pluginUntilBuild)

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription(closure {
            File("./plugin-description.md").readText().run { markdownToHTML(this) }
        })

        // Get the latest available change notes from the changelog file
        changeNotes(closure {
            changelog.getLatest().toHTML()
        })
    }

    runPluginVerifier {
        ideVersions(pluginVerifierIdeVersions)
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token(System.getenv("PUBLISH_TOKEN")) // pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels(pluginVersion.split('-').getOrElse(1) { "default" }.split('.').first())
    }
}

tasks.register<org.jetbrains.grammarkit.tasks.GenerateLexer>("generateLexer") {
    source = "src/grammar/cue.flex"
    targetDir = "src/main/java-gen/dev/monogon/cue/lang/lexer"
    targetClass = "_CueLexerGen"
    purgeOldFiles = true
}
