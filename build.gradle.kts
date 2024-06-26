plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	// Hibernate
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.mariadb.jdbc:mariadb-java-client:2.7.2")
	implementation("mysql:mysql-connector-java:8.0.28")
	// Spring Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	testImplementation("org.springframework.security:spring-security-test")
	// JWT Token
	implementation("io.jsonwebtoken:jjwt-api:0.11.5") // JJWT API
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5") // JJWT implementation
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5") // JJWT with Jackson JSON Processor
	// Model mapper
	implementation("org.modelmapper:modelmapper:3.0.0")
	// xml bind
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("org.glassfish.jaxb:jaxb-runtime:2.3.1")
	implementation("javax.activation:activation:1.1.1")
	// lombok
	annotationProcessor("org.projectlombok:lombok")
	compileOnly("org.projectlombok:lombok")
	// junit5
	testImplementation(platform("org.junit:junit-bom:5.8.2"))
	testImplementation("org.mockito:mockito-inline:3.7.7")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	// validation
	implementation("org.hibernate.validator:hibernate-validator:6.1.5.Final")
	implementation ("javax.validation:validation-api:2.0.1.Final")
	implementation("org.glassfish:javax.el:3.0.0")
	// log4j
	implementation("org.apache.logging.log4j:log4j-core:2.14.1")
	implementation("org.apache.logging.log4j:log4j-api:2.14.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
