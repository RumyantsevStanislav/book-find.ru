plugins {
	java
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	id("com.bmuschko.docker-spring-boot-application") version "9.3.1"
}

group = "ru.bookfind.parsers.labirint"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2022.0.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("io.github.openfeign:feign-httpclient")
	//implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.kafka:spring-kafka")
	// https://mvnrepository.com/artifact/org.jsoup/jsoup
	implementation("org.jsoup:jsoup:1.16.1")
    testImplementation("junit:junit:4.13.1")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	//developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

docker {
	springBootApplication {
		baseImage.set("eclipse-temurin:17-jdk-jammy")
		ports.set(listOf(8188, 8188))
		jvmArgs.set(listOf("-Dspring.profiles.active=prod", "-Xmx2048m"))
		images.set(setOf("stasrum/book-find.ru:labirint-parser"))
	}
}
