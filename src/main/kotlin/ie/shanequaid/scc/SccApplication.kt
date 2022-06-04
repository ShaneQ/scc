package ie.shanequaid.scc

import ie.shanequaid.scc.spring.properties.KeycloakClientProperties
import ie.shanequaid.scc.spring.properties.SCCBucketProperties
import ie.shanequaid.scc.spring.properties.SCCEmailProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@EnableConfigurationProperties(KeycloakClientProperties::class, SCCBucketProperties::class, SCCEmailProperties::class)
class SccApplication : WebMvcConfigurer

fun main(args: Array<String>) {
    runApplication<SccApplication>(*args)
}
