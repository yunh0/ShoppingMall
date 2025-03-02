plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.yunho"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
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
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation ("com.google.cloud:google-cloud-storage:2.1.1")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.querydsl:querydsl-jpa:${dependencyManagement.importedProperties["querydsl.version"]}:jakarta")
    implementation("com.querydsl:querydsl-core")
    implementation("com.querydsl:querydsl-collections")

    annotationProcessor("com.querydsl:querydsl-apt:${dependencyManagement.importedProperties["querydsl.version"]}:jakarta") // querydsl JPAAnnotationProcessor 사용 지정
    annotationProcessor("jakarta.annotation:jakarta.annotation-api") // java.lang.NoClassDefFoundError (javax.annotation.Generated) 대응 코드
    annotationProcessor("jakarta.persistence:jakarta.persistence-api") // java.lang.NoClassDefFoundError (javax.annotation.Entity) 대응 코드
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Querydsl 설정부
val generated = layout.buildDirectory.dir("generated")

// Querydsl QClass 파일 생성 위치 지정
tasks.withType<JavaCompile>().configureEach {
    options.generatedSourceOutputDirectory.set(generated.get().asFile)
}

// Java source set에 Querydsl QClass 위치 추가
sourceSets {
    named("main") {
        java.srcDirs(generated)
    }
}

// Gradle clean 시 QClass 디렉토리 삭제
tasks.named<Delete>("clean") {
    delete(generated)
}
