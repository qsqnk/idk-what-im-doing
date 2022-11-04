package domain

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@SpringBootConfiguration
@ComponentScan(basePackageClasses = [DomainSpringConfiguration::class])
@Import(
    DataSourceAutoConfiguration::class,
    FlywayAutoConfiguration::class,
    JooqAutoConfiguration::class,
)
@EnableAutoConfiguration
class DomainSpringConfiguration