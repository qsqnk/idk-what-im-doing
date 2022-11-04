import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.spring") version "1.6.21"
    id("org.springframework.boot") version "2.6.13"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    application
}

java.sourceCompatibility = JavaVersion.VERSION_11

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation(kotlin("reflect"))
    testImplementation("io.zonky.test:embedded-postgres:2.0.0")
    testImplementation("io.zonky.test:embedded-database-spring-test:2.1.2")

}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.flywaydb:flyway-core:8.5.12")
        classpath("org.jooq:jooq-codegen:3.14.15")
        classpath("io.zonky.test:embedded-postgres:1.3.1")
        classpath(enforcedPlatform("io.zonky.test.postgres:embedded-postgres-binaries-bom:14.3.0"))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.create("jooq-generate") {
    io.zonky.test.db.postgres.embedded.EmbeddedPostgres.builder()
        .start().use { embeddedPostgres ->
            val userName = "postgres"
            val dbName = "postgres"
            val schema = "public"
            org.flywaydb.core.Flyway.configure()
                .locations("filesystem:$projectDir/src/main/resources/db/migration")
                .schemas(schema)
                .dataSource(embeddedPostgres.getDatabase(userName, dbName))
                .load()
                .migrate()
            org.jooq.codegen.GenerationTool.generate(
                org.jooq.meta.jaxb.Configuration().withGenerator(
                    org.jooq.meta.jaxb.Generator().withDatabase(
                        org.jooq.meta.jaxb.Database()
                            .withInputSchema(schema)
                    ).withGenerate(
                        org.jooq.meta.jaxb.Generate()
                    ).withTarget(
                        org.jooq.meta.jaxb.Target()
                            .withPackageName("domain.db")
                            .withDirectory("$projectDir/src/main/java")
                    )
                ).withJdbc(
                    org.jooq.meta.jaxb.Jdbc().withUrl(embeddedPostgres.getJdbcUrl(userName, dbName))
                )
            )
        }
}

application {
    mainClass.set("MainKt")
}