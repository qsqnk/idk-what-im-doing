package application

import domain.DomainSpringConfiguration
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@SpringBootConfiguration
@ComponentScan(basePackageClasses = [ApplicationSpringConfiguration::class])
@Import(
    DomainSpringConfiguration::class,
)
class ApplicationSpringConfiguration