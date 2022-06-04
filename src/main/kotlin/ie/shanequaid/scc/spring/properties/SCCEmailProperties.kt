package ie.shanequaid.scc.spring.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("scc.email")
class SCCEmailProperties {
    lateinit var senderEmail: String
    lateinit var adminEmail: String
    lateinit var accessKey: String
    lateinit var secretKey: String

}