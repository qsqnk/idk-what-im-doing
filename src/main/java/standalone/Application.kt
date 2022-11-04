package standalone

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import web.WebSpringConfiguration

@SpringBootApplication
@Import(
    WebSpringConfiguration::class,
)
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java)
}

