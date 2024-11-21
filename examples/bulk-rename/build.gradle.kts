import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	`java-library`

	id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
	val jadxVersion = "1.5.1"
	val isJadxSnapshot = jadxVersion.endsWith("-SNAPSHOT")

	// use compile only scope to exclude jadx-core and its dependencies from result jar
	compileOnly("io.github.skylot:jadx-core:$jadxVersion") {
		isChanging = isJadxSnapshot
	}
	compileOnly("org.jetbrains:annotations:26.0.1")

	testImplementation("io.github.skylot:jadx-smali-input:$jadxVersion") {
		isChanging = isJadxSnapshot
	}

	testImplementation("io.github.skylot:jadx-gui:$jadxVersion") {
		isChanging = isJadxSnapshot
	}
	testImplementation("ch.qos.logback:logback-classic:1.5.12")
	testImplementation("org.assertj:assertj-core:3.26.3")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")
}

repositories {
	mavenLocal()
	mavenCentral()
	maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
	google()
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

version = System.getenv("VERSION") ?: "dev"

tasks {
	withType(Test::class) {
		useJUnitPlatform()
	}
	val shadowJar = withType(ShadowJar::class) {
		archiveClassifier.set("") // remove '-all' suffix
	}

	// copy result jar into "build/dist" directory
	register<Copy>("dist") {
		group = "jadx-plugin"
		dependsOn(shadowJar)
		dependsOn(withType(Jar::class))

		from(shadowJar)
		into(layout.buildDirectory.dir("dist"))
	}
}
