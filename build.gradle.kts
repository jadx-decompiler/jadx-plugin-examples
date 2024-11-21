import org.gradle.internal.impldep.org.eclipse.jgit.diff.Subsequence.a

plugins {
	// auto update dependencies with 'useLatestVersions' task
	id("se.patrikerdes.use-latest-versions") version "0.2.18"
	id("com.github.ben-manes.versions") version "0.51.0"
}

subprojects {
	apply(plugin = "se.patrikerdes.use-latest-versions")
	apply(plugin = "com.github.ben-manes.versions")
}
