/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/5.2.1/userguide/tutorial_java_projects.html
 */

plugins {
    val plugins = listOf(
	"java",
    "application")
	plugins.forEach {
		id(it)

	}
	id("org.springframework.boot") version "2.1.3.RELEASE"
}

application {
	mainClassName = "com.yuuitanabe.pipres.web.BMP180Web"
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    mavenCentral()
	val repos = listOf("repo.spring.io/snapshot",
			           "repo.spring.io/milestone")
	repos.forEach {
	maven(url = StringBuffer("http://" + it))
	}
}

dependencies {
    // This dependency is found on compile classpath of this component and consumers.
	val springbootver = "2.1.3.RELEASE"
	val springver = "5.1.5.RELEASE"
    val dependencies = listOf("com.google.guava:guava:27.0.1-jre",
    						  "org.springframework.boot:spring-boot-starter-web:$springbootver",
    						  "org.springframework.boot:spring-boot-devtools:$springbootver",
       						  "org.springframework:spring-core:$springver",
       						  "org.springframework:spring-jdbc:$springver",
    						  "com.pi4j:pi4j-core:1.2"
	)
    dependencies.forEach {
    	implementation(it)
	}
	runtime(files("lib/ojdbc8.jar"))
    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
}

