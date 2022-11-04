package web

import application.ApplicationSpringConfiguration
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@SpringBootConfiguration
@ComponentScan(basePackageClasses = [WebSpringConfiguration::class])
@Import(
    ApplicationSpringConfiguration::class,
)
class WebSpringConfiguration